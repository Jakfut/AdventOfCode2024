package day18

import _template.*
import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day18/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val grid:MutableGrid = List(SIZE_X) { index -> List(SIZE_Y){'.'} }.toMutableGrid()
    val corruptedMemory:MutableList<Pair<Int, Int>> = mutableListOf()

    lineList.forEach {
        corruptedMemory.add(Pair(it.split(",")[0].toInt(), it.split(",")[1].toInt()))
    }

    for (i in 0 until ITERATIONS){
        grid[corruptedMemory[i].second][corruptedMemory[i].first] = '#'
    }

    for (i in ITERATIONS until corruptedMemory.size){
        grid[corruptedMemory[i].second][corruptedMemory[i].first] = '#'
        if ((findShortestCost(grid, Vec2(0, 0), Vec2(SIZE_X - 1, SIZE_Y - 1))) == -1){
            println("Corrupted memory at ${corruptedMemory[i].first}, ${corruptedMemory[i].second}")
            break
        }
    }
}