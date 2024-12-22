package day22

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day19/example").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }


}