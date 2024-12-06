package day03

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.abs

fun main(){
    val inputStream: InputStream = File("src/day03/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    var score = 0

    lineList.forEach {
        Regex("(mul\\(\\d+,\\d+\\))").findAll(it).forEach { matchResult ->
            val (a, b) = matchResult.value.substring(4, matchResult.value.length - 1).split(",")
            score += a.toInt() * b.toInt()
        }
    }

    println(score)
}