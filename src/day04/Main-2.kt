package day04

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.abs

fun main(){
    val inputStream: InputStream = File("src/day04/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    var score: Int = 0

    val mas = Regex("MAS")
    val sam = Regex("SAM")

    val charArray = lineList.map { it.toCharArray() }.toTypedArray()

    charArray.forEachIndexed { index, chars ->
        val matches = Regex("A").findAll(chars.joinToString(""))
        matches.forEach {
            if (it.range.first == 0 || it.range.first == chars.size - 1)
                return@forEach

            if (index == 0 || index == charArray.size - 1)
                return@forEach

            var dig1 = charArray[index - 1][it.range.first - 1].toString()
            dig1 += charArray[index][it.range.first].toString()
            dig1 += charArray[index + 1][it.range.first + 1].toString()

            var dig2 = charArray[index - 1][it.range.first + 1].toString()
            dig2 += charArray[index][it.range.first].toString()
            dig2 += charArray[index + 1][it.range.first - 1].toString()

            if (mas.containsMatchIn(dig1) || sam.containsMatchIn(dig1)){
                if (mas.containsMatchIn(dig2) || sam.containsMatchIn(dig2)){
                    score += 1
                }
            }
        }
    }

    println(score)
}