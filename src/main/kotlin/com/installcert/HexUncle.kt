package com.installcert

object HexUncle {

    private val HexDigits = "0123456789abcdef".toCharArray()

    fun toHexString(byteArray: ByteArray): String {

        val string = StringBuilder(byteArray.size * 3)

        byteArray.forEach { b ->
            val byte = b.toInt() and 0xff
            val rightShift = byte shr 4
            val and = byte and 15
            string.append(HexDigits[rightShift])
            string.append(HexDigits[and])
            string.append(' ')
        }

        return string.toString()
    }
}
