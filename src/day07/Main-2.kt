package day07

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day07/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    var score = 0L
    val operators = listOf("+", "*", "|")

    lineList.forEach {
        val target = it.split(":")[0].toLong()
        val numbers = it.split(":")[1].trim().split(" ").map { it.toLong() }.toMutableList()

        for (op in generateCombinations(operators, numbers.size - 1)) {
            var result = numbers[0]
            for (i in op.indices) {
                result = when (op[i]) {
                    "+" -> result + numbers[i + 1]
                    "*" -> result * numbers[i + 1]
                    "|" -> result.toString().plus(numbers[i + 1].toString()).toLong()
                    else -> throw IllegalArgumentException("Invalid operator $op")
                }
            }
            if (result == target) {
                score += target
                break
            }
        }
    }

    println(score)
}