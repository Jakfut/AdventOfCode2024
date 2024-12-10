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

    val trailHeads:MutableMap<Int, MutableSet<Pair<Int, Int>>> = mutableMapOf()

    //println(result)

    result.forEach {
        val key = it.first
        val value = it.second
        val x = value.keys.first()
        val y = value.values.first()

        if(trailHeads.containsKey(key)){
            trailHeads[key]!!.add(Pair(x, y))
        }else{
            trailHeads[key] = mutableSetOf(Pair(x, y))
        }
    }

    //println(trailHeads)

    var score = 0

    trailHeads.forEach {
        score += it.value.size
    }

    println(score)
}

fun findTheNine(x: Int, y: Int, index: Int, trailResult: MutableList<Pair<Int, MutableMap<Int, Int>>>, lineList: MutableList<String>){
    val current = lineList[y][x]

    if(current == '9'){
        trailResult.add(Pair(index, mutableMapOf(Pair(x, y))))
        return
    }
    // check for neighbors, if the neighbor is current + 1 start findTheNine from that position
    // left
    if(x + 1 < lineList[y].length && lineList[y][x + 1] == current + 1){
        findTheNine(x + 1, y, index, trailResult, lineList)
    }
    // right
    if(x - 1 >= 0 && lineList[y][x - 1] == current + 1){
        findTheNine(x - 1, y, index, trailResult, lineList)
    }
    // down
    if(y + 1 < lineList.size && lineList[y + 1][x] == current + 1){
        findTheNine(x, y + 1, index, trailResult, lineList)
    }
    // up
    if(y - 1 >= 0 && lineList[y - 1][x] == current + 1){
        findTheNine(x, y - 1, index, trailResult, lineList)
    }
}