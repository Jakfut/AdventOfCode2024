package day02

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.abs

fun main(){
    val inputStream: InputStream = File("src/day02/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    var score = 0

    lineList.forEach {
        val report: MutableList<Int> = it.split(" ").map { it.toInt() }.toMutableList()

        for (i in 1..report.lastIndex) {
            if (report[i] > report[i-1]) break
            if (abs(report[i-1] - report[i]) > 3 || report[i-1] == report[i]) break
            if (i == report.lastIndex) score++
        }

        for (i in 1..report.lastIndex) {
            if (report[i] < report[i-1]) break
            if (abs(report[i-1] - report[i]) > 3 || report[i-1] == report[i]) break
            if (i == report.lastIndex) score++
        }
    }

    println(score)
}