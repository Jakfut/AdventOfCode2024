package day18

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day15/example3").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }
}
