package day13

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day13/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    var buttonA = Pair(0,0)
    var buttonB = Pair(0,0)
    var target:Pair<Int,Int>

    var totalCost = 0

    lineList.forEach {
        if (it.contains("A")){
            buttonA = Pair(it.split("X+")[1].split(",")[0].toInt(), it.split("Y+")[1].split(" ")[0].toInt())
        } else if (it.contains("B")){
            buttonB = Pair(it.split("X+")[1].split(",")[0].toInt(), it.split("Y+")[1].split(" ")[0].toInt())
        } else if (it.contains("P")){
            target = Pair(it.split("X=")[1].split(",")[0].toInt(), it.split("Y=")[1].split(" ")[0].toInt())

            /*println(buttonA)
            println(buttonB)
            println(target)

            println(getCost(buttonA, buttonB, target))*/

            totalCost += getCost(buttonA, buttonB, target)
        }
    }

    println(totalCost)
}

fun getCost(buttonA: Pair<Int,Int>, buttonB: Pair<Int,Int>, target: Pair<Int,Int>):Int{
    var cheapest = Int.MAX_VALUE
    for (i in 0..100){
        for (j in 0..100){
            if (buttonA.first * i + buttonB.first * j == target.first && buttonA.second * i + buttonB.second * j == target.second) {
                if (i * 3 + j < cheapest) {
                    cheapest = i * 3 + j
                }
            }
        }
    }

    if (cheapest == Int.MAX_VALUE){
        return 0
    }

    return cheapest
}