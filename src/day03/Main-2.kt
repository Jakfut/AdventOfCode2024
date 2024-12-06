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

    val inputList = mutableListOf<String>()
    val lineString = lineList.toString()

    lineString.split("don't()").forEachIndexed { i, s ->
        if (i == 0)
            inputList.add(s)
        else{
            val doList = s.split("do()").toMutableList()
            doList.removeFirst()
            doList.forEach {
                inputList.add(it)
            }
        }
    }

    inputList.forEach {
        //println(it)
        Regex("(mul\\(\\d+,\\d+\\))").findAll(it).forEach { matchResult ->
            val (a, b) = matchResult.value.substring(4, matchResult.value.length - 1).split(",")
            score += a.toInt() * b.toInt()
        }
    }

    println(score)
}