package day14

import java.io.File
import java.io.InputStream

val maxX = 101 - 1
val maxY = 103 - 1

fun main(){
    val inputStream: InputStream = File("src/day14/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val robots: MutableList<Robot> = mutableListOf()

    lineList.forEach {
        val position = it.substringBefore(" ").substringAfter("=")
        val velocity = it.substringAfter(" ").substringAfter("=")

        robots.add(
            Robot(
                Pair(
                    position.substringAfter("<").substringBefore(",").toInt(),
                    position.substringAfter(",").substringBefore(">").toInt()
                ),
                Pair(
                    velocity.substringAfter("<").substringBefore(",").toInt(),
                    velocity.substringAfter(",").substringBefore(">").toInt()
                )
            )
        )
    }

    //println(robots)

    for (j in 0..<100){
        for (i in robots.indices){
            robots[i].tick()
        }
        println("After ${j+1} seconds")
        visualize(robots)
    }

    /*// calculate the robots in the first quadrant
    println(calculateRobotsInArea(robots, 0, (maxX / 2) - 1, 0, (maxY/ 2) - 1))
    // calculate the robots in the second quadrant
    println(calculateRobotsInArea(robots, (maxX / 2), maxX, 0, (maxY/ 2) - 1))
    // calculate the robots in the third quadrant
    println(calculateRobotsInArea(robots, 0, (maxX / 2) - 1, (maxY/ 2) + 1, maxY))
    // calculate the robots in the fourth quadrant
    println(calculateRobotsInArea(robots, (maxX / 2) + 1, maxX, (maxY/ 2) + 1, maxY))*/

    println(calculateRobotsInArea(robots, 0, (maxX / 2) - 1, 0, (maxY/ 2) - 1) *
            calculateRobotsInArea(robots, (maxX / 2), maxX, 0, (maxY/ 2) - 1) *
            calculateRobotsInArea(robots, 0, (maxX / 2) - 1, (maxY/ 2) + 1, maxY) *
            calculateRobotsInArea(robots, (maxX / 2) + 1, maxX, (maxY/ 2) + 1, maxY))

    visualize(robots)
}

data class Robot(var position: Pair<Int, Int>, var velocity: Pair<Int, Int>) {
    fun tick(){
        position = Pair(position.first + velocity.first, position.second + velocity.second)

        // check for out of bounds
        if (position.first < 0){
            position = Pair(maxX + 1 + position.first, position.second)
        }

        if (position.first > maxX){
            position = Pair(position.first - 1 - maxX, position.second)
        }

        if (position.second < 0){
            position = Pair(position.first, maxY + 1 + position.second)
        }

        if (position.second > maxY){
            position = Pair(position.first, position.second - 1 - maxY)
        }
    }
}

fun visualize(robots: MutableList<Robot>){
    val grid = Array(maxY + 1) { CharArray(maxX + 1) { '.' } }

    robots.forEach {
        grid[it.position.second][it.position.first] = '#'
    }

    for (i in 0..maxY ){
        for (j in 0..maxX ){
            print(grid[i][j])
        }
        println()
    }
}

fun calculateRobotsInArea(robots: MutableList<Robot>, minX: Int, maxX: Int, minY: Int, maxY: Int): Int {
    var count = 0

    for (i in robots){
        if (i.position.first in minX..maxX && i.position.second in minY..maxY){
            count++
        }
    }

    return count
}