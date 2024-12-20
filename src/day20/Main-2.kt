package day20

import _template.Grid
import java.io.File
import java.io.InputStream


fun main(){
    val inputStream: InputStream = File("src/day16/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val grid: Grid = lineList.map { it.toCharArray().toList() }.toList()
}