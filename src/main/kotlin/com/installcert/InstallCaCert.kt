package com.installcert

import java.io.*
import java.io.File.separatorChar
import java.security.KeyStore
import java.security.MessageDigest
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Class used to add the server's certificate to the TrustKeyStore
 * with your trusted certificates.
 */
object InstallCaCert {

    val DefaultTrustStorePassword = "changeit"

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {

        if (args.size == 1 || args.size == 2) {

            val c = args[0].split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            val host = c[0]
            val port = if (c.size == 1) 443 else Integer.parseInt(c[1])
            val p = if (args.size == 1) DefaultTrustStorePassword else args[1]
            val passphrase = p.toCharArray()

            cacert(host, port, passphrase)

        } else {
            println("Usage: java -jar InstallCaCert.jar <host>[:port] [passphrase]")
            return
        }
    }

    private fun cacert(host: String, port: Int, passphrase: CharArray) {
        val jsseCacertsFile = jsseCacertsFilePath()

        println("=============================================================================================")
        println("Loading existing certKeyStore(Private keys) $jsseCacertsFile with passphrase ${String(passphrase)}")

        val certKeyStore = loadPrivateKeystore(jsseCacertsFile, passphrase)

        val context = SSLContext.getInstance("TLS")
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(certKeyStore)

        val defaultTrustManager = tmf.trustManagers[0] as X509TrustManager
        val tm = SavingTrustManager(defaultTrustManager)
        context.init(null, arrayOf<TrustManager>(tm), null)
        val factory = context.socketFactory

        println("Opening connection to $host:$port............................................................")
        println("=============================================================================================")

        val serverSocket = factory.createSocket(host, port) as SSLSocket
        serverSocket.soTimeout = 10000
        try {
            println("Starting TLS handshake with $host:$port")
            serverSocket.startHandshake()
            serverSocket.close()
            println()
            println("No errors handshaking $host:$port, server certificate is already trusted")
            println("=============================================================================================")
        } catch (e: SSLException) {
            println()
            e.printStackTrace(System.out)
        }

        val certChain = tm.chain
        if (certChain == null) {
            println("=======================================================================")
            println("Could not obtain server certificate chain for $host")
            println("=======================================================================")
            return
        }

        val reader = BufferedReader(InputStreamReader(System.`in`))

        println("=============================================================================================")
        println("Server sent " + certChain.size + " certificate(s):")

        val sha1 = MessageDigest.getInstance("SHA1")
        val md5 = MessageDigest.getInstance("MD5")

        for (i in certChain.indices) {
            val cert = certChain[i]
            println(" " + (i + 1) + " Subject " + cert.subjectDN)
            println("   Issuer  " + cert.issuerDN)
            sha1.update(cert.encoded)
            println("   sha1    " + HexUncle.toHexString(sha1.digest()))
            md5.update(cert.encoded)
            println("   md5     " + HexUncle.toHexString(md5.digest()))
            println()
        }
        println("=============================================================================================")

        println()
        print("Enter [or certificate-number] to add certificate to trusted keystore or 'q' to quit: [1/q]")
        val line = reader.readLine().trim { it <= ' ' }
        val certIndex: Int
        try {
            certIndex = if (line.isEmpty()) 0 else Integer.parseInt(line) - 1
        } catch (e: NumberFormatException) {
            println("KeyStore(Public KeyStore) not changed")
            return
        }

        val cacert = certChain[certIndex]
        val certAlias = host + "-" + (certIndex + 1)
        certKeyStore.setCertificateEntry(certAlias, cacert)

        val cacertOutputStream = FileOutputStream("jssecacerts")
        certKeyStore.store(cacertOutputStream, passphrase)
        cacertOutputStream.close()

        println()
        println(cacert)
        println()
        println("Added certificate to cert-keystore 'jssecacerts' using alias '" + certAlias + "'")
        println("===============================================================================")
    }

    private fun loadPrivateKeystore(jsseCacertsFile: File, passphrase: CharArray): KeyStore {
        val `in` = FileInputStream(jsseCacertsFile)
        val certKeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        certKeyStore.load(`in`, passphrase)
        `in`.close()
        return certKeyStore
    }

    private fun jsseCacertsFilePath(): File {
        var jsseCacertsFile = File("jssecacerts")

        if (!jsseCacertsFile.isFile) {
            val jreSecurityDir = File(System.getProperty("java.home") + separatorChar + "lib" + separatorChar + "security")

            jsseCacertsFile = File(jreSecurityDir, "jssecacerts")

            if (!jsseCacertsFile.isFile) {
                jsseCacertsFile = File(jreSecurityDir, "cacerts")
            }
        }
        return jsseCacertsFile
    }

    private class SavingTrustManager internal constructor(private val tm: X509TrustManager) : X509TrustManager {
        var chain: Array<X509Certificate>? = null

        override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()

        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) =
                throw UnsupportedOperationException()

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            this.chain = chain
            tm.checkServerTrusted(chain, authType)
        }
    }
}

private infix fun Byte.shr(shiftBy: Int): Int = this.toInt() shl shiftBy