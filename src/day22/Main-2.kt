package day22

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day22/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val secretNumbers = lineList.map { it.toLong() }
    val sequenceMap:MutableMap<String, Long> = mutableMapOf()


    secretNumbers.forEach {
        val currentSequenceMap:MutableMap<String, Long> = mutableMapOf()
        var secretNumber = it
        var lastSecretNumber = it
        val currentSequence:MutableList<Long> = mutableListOf()

        for (i in 1..2000) {
            secretNumber = nextSecretNumber(secretNumber)

            currentSequence.add(secretNumber % 10 - lastSecretNumber % 10)

            if (currentSequence.size == 5){
                val sequence = currentSequence.subList(0, 4).joinToString("")
                if (!currentSequenceMap.containsKey(sequence))
                    currentSequenceMap[sequence] = lastSecretNumber % 10
                    currentSequence.removeAt(0)
            }
            lastSecretNumber = secretNumber
        }

        currentSequenceMap.forEach {
            if (sequenceMap.containsKey(it.key)){
                sequenceMap[it.key] = sequenceMap[it.key]!! + it.value
            } else {
                sequenceMap[it.key] = it.value
            }
        }
    }

    println(sequenceMap.maxOf { it.value })
}