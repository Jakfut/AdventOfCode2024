package day23

import java.io.File
import java.io.InputStream

fun main() {
    val inputStream: InputStream = File("src/day23/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val connections: MutableSet<Pair<String, String>> = mutableSetOf()
    val uniqueLoops: MutableSet<Triple<String, String, String>> = mutableSetOf()
    var score = 0

    lineList.forEach {
        val (from, to) = it.split("-")
        connections.add(Pair(from, to))
        connections.add(Pair(to, from))
    }

    connections.forEach { connection ->
        val second = connections.filter { it.first == connection.second && it.second != connection.first }
        second.forEach { secondConnection ->
            val third = connections.filter { it.first == secondConnection.second && it.second == connection.first }
            third.forEach { _ ->
                val sorted = listOf(connection.first, connection.second, secondConnection.second).sorted()
                uniqueLoops.add(Triple(sorted[0], sorted[1], sorted[2]))
            }
        }
    }

    println(uniqueLoops.size)

    uniqueLoops.forEach {
        if (it.first.first() == 't' || it.second.first() == 't' || it.third.first() == 't') {
            score++
        }
    }

    println(score)
}