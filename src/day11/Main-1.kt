package day11

import java.io.File
import java.io.InputStream
import java.util.LinkedList

fun main(){
    val inputStream: InputStream = File("src/day11/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val list = lineList[0].split(" ").map { it.toLong() }.toCollection(LinkedList())

    println(list)

    for (i in 0..24){
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

    println(list.size)

    println(list.toMutableSet().size)
}