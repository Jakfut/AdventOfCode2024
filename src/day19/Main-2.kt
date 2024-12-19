package day19

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day19/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val towels:MutableList<String> = mutableListOf()
    val targets:MutableList<String> = mutableListOf()
    var isTowel = true
    var score = 0

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

    targets.forEach {
        if (findSolutionV2(towels, "", it) != "")
            score++
    }

    println(score)
}

fun findSolutionV2(towels:MutableList<String>, curString:String,  target: String) : String {
    if (curString == target)
        return curString

    towels.forEach {
        if (target.startsWith(curString + it)) {
            val result = findSolution(towels, curString + it, target)
            if (result != "")
                return result
        }
    }

    return ""
}