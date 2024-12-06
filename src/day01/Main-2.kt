package day01

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.abs

fun main(){
    val inputStream: InputStream = File("src/day01/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val leftList = mutableListOf<Int>()
    val rightList = mutableListOf<Int>()

    var score: Int = 0

    lineList.forEach {
        leftList.add(it.split(" ").first().toInt())
        rightList.add(it.split(" ").last().toInt())
    }

    leftList.forEach {
        for (i in 0..leftList.lastIndex) {
            if (it == rightList[i]) {
                score += it
            }
        }
    }

    println(score)
}