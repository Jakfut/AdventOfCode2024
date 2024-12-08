package day08

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day08/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val maxX = lineList[0].length
    val maxY = lineList.size
    val antennas = mutableListOf<Pair<Char, Pair<Int, Int>>>()
    val antinodes = mutableSetOf<Pair<Int, Int>>()

    lineList.forEachIndexed { y, s ->
        s.forEachIndexed { x, c ->
            if (c != '.'){
                antennas.add(Pair(c, Pair(x, y)))
            }
        }
    }

    val groupedAntennas = antennas.groupBy { it.first }

    for ((char, elements) in groupedAntennas) {
        for (i in elements.indices){
            for (j in i+1..<elements.size){
                val (x1, y1) = elements[i].second
                val (x2, y2) = elements[j].second
                val dir1 = Pair(x2 - x1, y2 - y1)
                val dir2 = Pair(x1 - x2, y1 - y2)

                for (k in 0..<100){
                    val antinode1 = Pair(x1 + dir2.first * k, y1 + dir2.second * k)
                    val antinode2 = Pair(x2 + dir1.first * k, y2 + dir1.second * k)
                    if (antinode1.first in 0..<maxX && antinode1.second in 0..<maxY){
                        antinodes.add(antinode1)
                    }
                    if (antinode2.first in 0..<maxX && antinode2.second in 0..<maxY){
                        antinodes.add(antinode2)
                    }
                }
            }
        }
    }

    println(antinodes.size)
}