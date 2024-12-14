package day14

import java.io.File
import java.io.InputStream


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

    for (j in 0..<10000){
        for (i in robots.indices){
            robots[i].tick()
        }
        if (checkForTree(visualizeV2(robots))){
            println("After ${j+1} seconds")
            visualize(robots)
            return
        }
    }

    /*// calculate the robots in the first quadrant
    println(calculateRobotsInArea(robots, 0, (maxX / 2) - 1, 0, (maxY/ 2) - 1))
    // calculate the robots in the second quadrant
    println(calculateRobotsInArea(robots, (maxX / 2), maxX, 0, (maxY/ 2) - 1))
    // calculate the robots in the third quadrant
    println(calculateRobotsInArea(robots, 0, (maxX / 2) - 1, (maxY/ 2) + 1, maxY))
    // calculate the robots in the fourth quadrant
    println(calculateRobotsInArea(robots, (maxX / 2) + 1, maxX, (maxY/ 2) + 1, maxY))*/

    /*println(calculateRobotsInArea(robots, 0, (maxX / 2) - 1, 0, (maxY/ 2) - 1) *
            calculateRobotsInArea(robots, (maxX / 2), maxX, 0, (maxY/ 2) - 1) *
            calculateRobotsInArea(robots, 0, (maxX / 2) - 1, (maxY/ 2) + 1, maxY) *
            calculateRobotsInArea(robots, (maxX / 2) + 1, maxX, (maxY/ 2) + 1, maxY))

    visualize(robots)*/
}

fun visualizeV2(robots: MutableList<Robot>) : MutableList<String>{
    val grid = MutableList(maxY + 1) { MutableList(maxX + 1) { '.' } }

    robots.forEach {
        grid[it.position.second][it.position.first] = '#'
    }

    return grid.map { it.joinToString("") }.toMutableList()
}

fun checkForTree(grid: MutableList<String>): Boolean{
    grid.forEach {
        if (it.contains("########")){
            return true
        }
    }
    return false
}