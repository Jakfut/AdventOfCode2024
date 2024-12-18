package day18

import _template.*
import java.io.File
import java.io.InputStream

const val SIZE_X = 71
const val SIZE_Y = 71
const val ITERATIONS = 1024
const val WALL = '#'
val DIRECTIONS = listOf(Vec2.UP, Vec2.DOWN, Vec2.LEFT, Vec2.RIGHT)

fun main(){
    val inputStream: InputStream = File("src/day18/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val grid:MutableGrid = List(SIZE_X) { index -> List(SIZE_Y){'.'} }.toMutableGrid()
    val corruptedMemory:MutableList<Pair<Int, Int>> = mutableListOf()
    var score = 0

    lineList.forEach {
        corruptedMemory.add(Pair(it.split(",")[0].toInt(), it.split(",")[1].toInt()))
    }

    for (i in 0 until ITERATIONS){
        grid[corruptedMemory[i].second][corruptedMemory[i].first] = '#'
    }

    //grid.forEach { println(it) }

    println(findShortestCost(grid, Vec2(0, 0), Vec2(SIZE_X - 1, SIZE_Y - 1)))
}

fun findNeighbors(grid: Grid, current: Vec2) : Map<Vec2, Long>{
    return DIRECTIONS.map { it + current }.filter { grid.inGrid(it) && grid.at(it) != WALL }.associateWith { 1L }
}

fun findShortestCost(grid: Grid, start: Vec2, end: Vec2) : Long{
    val visited = mutableSetOf<Vec2>()
    val queue = mutableListOf<Pair<Vec2, Long>>()

    queue.add(Pair(start, 0))

    while (queue.isNotEmpty()){
        val (current, cost) = queue.removeAt(0)

        if (current == end){
            return cost
        }

        visited.add(current)

        findNeighbors(grid, current).forEach {
            if (it.key !in visited){
                queue.add(Pair(it.key, cost + it.value))
            }
        }
    }

    return -1
}