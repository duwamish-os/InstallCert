import com.installcert.HexUncle

object HexUncleSpec {

    @JvmStatic
    fun main(args: Array<String>) {
        val bytes = "prayagupd".toByteArray()
        //val a = arrayOf(-60, -72, 28, -107, 104, 26, -54, -53, 100, 76, 7, 112, -83, -43, 100, -31)
        println(HexUncle.toHexString(bytes))
    }

}