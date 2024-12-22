package day22

import java.io.File
import java.io.InputStream

fun main() {
    val inputStream: InputStream = File("src/day22/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val secretNumbers = lineList.map { it.toLong() }
    var score = 0L

    secretNumbers.forEach {
        var secretNumber = it
        for (i in 1..2000) {
            secretNumber = nextSecretNumber(secretNumber)
        }
        score += secretNumber
    }
    println(score)
}

fun nextSecretNumber(secretNumber: Long): Long {
    return nextSecretNumber3(nextSecretNumber2(nextSecretNumber1(secretNumber)))
}

fun nextSecretNumber1(secretNumber: Long): Long {
    return mix(secretNumber, secretNumber * 64).prune()
}

fun nextSecretNumber2(secretNumber: Long): Long {
    return mix(secretNumber, secretNumber / 32).prune()
}

fun nextSecretNumber3(secretNumber: Long): Long {
    return mix(secretNumber, secretNumber * 2048).prune()
}

fun Long.prune(): Long {
    return this % 16777216
}

fun mix(secretNumber: Long, value: Long): Long {
    return secretNumber xor value
}