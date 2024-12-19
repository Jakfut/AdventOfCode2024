package day19

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day19/example").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val towels:MutableList<String> = mutableListOf()
    val targets:MutableList<String> = mutableListOf()
    var isTowel = true

    lineList.forEach {
        if (it == "")
            isTowel = false
        else if (isTowel)
            towels.addAll(it.split(",").toMutableList()).also { towels.replaceAll { it.replace(" ", "") } }
        else
            targets.add(it)
    }

    towels.sortByDescending { it.length }
    //println(towels)
    //println(targets)

    var possibleSolutions = 0L
    targets.forEach {
        possibleSolutions += findSolutionV2(towels, "", it)
    }

    println(possibleSolutions)
}

fun findSolutionV2(towels:MutableList<String>, curString:String,  target: String, cache: MutableMap<String, Long> = mutableMapOf()) : Long {
    if (curString == target) {
        return 1
    }

    if (cache.containsKey(curString)) {
        return cache[curString]!!
    }

    var count = 0L
    towels.forEach {
        if (target.startsWith(curString + it)) {
            count += findSolutionV2(towels, curString + it, target, cache)
        }
    }

    cache[curString] = count
    return count
}