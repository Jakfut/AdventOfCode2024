package day04

import java.io.File
import java.io.InputStream
import java.util.*
import kotlin.math.abs

fun main(){
    val inputStream: InputStream = File("src/day04/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    var score: Int = 0

    lineList.forEach {
        // regex check for occurrence of "XMAS" and "SAMX" in the string, multiple occurrences are allowed, for every occurrence add 1 to the score
        val xmas = Regex("XMAS")
        val samx = Regex("SAMX")

        score += xmas.findAll(it).count()
        score += samx.findAll(it).count()
    }

    // Rotate the lines by 45 degrees
    //val rotated45 = rotate45(lineList.size, lineList[0].length, lineList.map { it.toCharArray() }.toTypedArray())
    val rotated45 = rotate45(lineList)

    rotated45.forEach {
        // regex check for occurrence of "XMAS" and "SAMX" in the string, multiple occurrences are allowed, for every occurrence add 1 to the score
        val xmas = Regex("XMAS")
        val samx = Regex("SAMX")

        score += xmas.findAll(it).count()
        score += samx.findAll(it).count()
    }

    //rotated45.forEach { println(it) }

    // Rotate the lines by 90 degrees
    val rotated90 = lineList.mapIndexed { i, s -> lineList.map { it[i] }.joinToString("") }.toMutableList()

    rotated90.forEach {
        // regex check for occurrence of "XMAS" and "SAMX" in the string, multiple occurrences are allowed, for every occurrence add 1 to the score
        val xmas = Regex("XMAS")
        val samx = Regex("SAMX")

        score += xmas.findAll(it).count()
        score += samx.findAll(it).count()
    }

    // Rotate the lines by 135 degrees
    val rotated135 = rotate45(lineList.map { it.reversed() }.toMutableList())

    //rotated135.forEach { println(it) }

    rotated135.forEach {
        // regex check for occurrence of "XMAS" and "SAMX" in the string, multiple occurrences are allowed, for every occurrence add 1 to the score
        val xmas = Regex("XMAS")
        val samx = Regex("SAMX")

        score += xmas.findAll(it).count()
        score += samx.findAll(it).count()
    }

    println(score)
}


fun rotate45(lines: MutableList<String>): MutableList<String> {
    val rotated = mutableListOf<String>()

    for (i in lines.size downTo -lines.size) {
        var stringToAdd = ""
        for (j in i..lines.size) {
            //println(Pair(j, j - i))
            if (j < lines.size && j - i >= 0 && j - i < lines.size && j > -2 && j + 1 < lines[j - i].length) {
                stringToAdd += lines[j - i].substring(j + 1, j + 2)
            }
        }
        rotated.add(stringToAdd)
    }
    return rotated
}