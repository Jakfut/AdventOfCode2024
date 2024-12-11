package day11

import java.io.File
import java.io.InputStream
import java.util.LinkedList

fun main(){
    val inputStream: InputStream = File("src/day11/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val list = lineList[0].split(" ").map { it.toLong() }.toCollection(LinkedList())
    var totalStone = 0L

    println(list)

    var occurrences:MutableMap<Long, Long> = list.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()

    for (i in 0..14){
        occurrences = iterate5(occurrences)
    }

    for (entry in occurrences.entries) {
        totalStone += entry.value
    }

    println("Stones 25 iterations: $totalStone")

    println("Unique stone: ${occurrences.size}")
}

fun iterate(list: LinkedList<Long>) {
    val iterator = list.listIterator()
    while (iterator.hasNext()) {
        val current = iterator.next()

        if (current == 0L) {
            iterator.set(1L)
            continue
        }

        val currentStr = current.toString()
        if (currentStr.length % 2 == 0) {
            val left = currentStr.substring(0, currentStr.length / 2).toLong()
            val right = currentStr.substring(currentStr.length / 2).toLong()
            iterator.set(left)
            iterator.add(right)
            continue
        }

        iterator.set(current * 2024)
    }
}

fun iterate5(occurrences: MutableMap<Long, Long>) : MutableMap<Long, Long>{
    var i = 0
    val newOccurrences: MutableMap<Long, Long> = mutableMapOf()

    while (i < occurrences.size) {
        val newList = LinkedList<Long>()
        newList.add(occurrences.keys.elementAt(i))

        for (j in 0..4){
            iterate(newList)
        }

        newList.forEach {
            newOccurrences[it] = newOccurrences.getOrDefault(it, 0) + occurrences.getOrDefault(occurrences.keys.elementAt(i), 0)
        }
        i++
    }
    return newOccurrences
}