package day05

import java.io.File
import java.io.InputStream
import kotlin.math.abs

fun main(){
    val inputStream: InputStream = File("src/day05/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val rules:MutableList<Pair<Int, Int>> = mutableListOf()
    val updates:MutableList<MutableList<Int>> = mutableListOf()
    var score = 0

    lineList.forEach {
        if (it == ""){
            score++
            return@forEach
        }

        if (score == 0){
            rules.add(Pair(
                it.split("|")[0].toInt(),
                it.split("|")[1].toInt()
            ))
        } else {
            updates.add(it.split(",").map { it.toInt() }.toMutableList())
        }
    }

    score = 0

    updates.forEach { update ->
        var isUpdateValid = true
        rules.forEach { rule ->
            if (update.contains(rule.second)){
                val indexOfSecond = update.indexOf(rule.second)
                if (update.contains(rule.first)){
                    val indexOfFirst = update.indexOf(rule.first)
                    if (indexOfFirst > indexOfSecond){
                        isUpdateValid = false
                        return@forEach
                    }
                }
            }
        }
        if (isUpdateValid){
            score += update[update.size / 2]
        }
    }

    println(score)
}