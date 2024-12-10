package day10

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day10/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val result:MutableList<Pair<Int, MutableMap<Int, Int>>> = mutableListOf()

    var index = 0
    for(i in 0..<lineList.size){
        for(j in 0..<lineList[i].length){
            if(lineList[i][j] == '0'){
                findTheNine(j, i, index, result, lineList)
                index++
            }
        }
    }

    //println(result)

    var score = 0

    result.forEach {
        score += it.second.size
    }

    println(score)
}