package day13

import java.io.File
import java.io.InputStream

fun main() {
    val inputStream: InputStream = File("src/day13/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }
}