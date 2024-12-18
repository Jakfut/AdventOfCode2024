package day18

import _template.*
import java.io.File
import java.io.InputStream
import java.util.PriorityQueue

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

fun findShortestCost(grid: Grid, start: Vec2, end: Vec2) : Int{
    val distances = mutableMapOf<Vec2, Long>()
    val queue = PriorityQueue<Vec2>(compareBy { t -> distances.getOrElse(t) { Long.MAX_VALUE } })

    distances[start] = 0
    queue.add(start)

    while (queue.isNotEmpty()){
        val current = queue.poll()

        if (current == end){
            return distances[current]!!.toInt()
        }

        val neighbors = findNeighbors(grid, current)

        for (neighbor in neighbors.keys){
            val newDistance = distances[current]!! + neighbors[neighbor]!!

            if (newDistance < distances.getOrElse(neighbor) { Long.MAX_VALUE }){
                distances[neighbor] = newDistance
                queue.add(neighbor)
            }
        }
    }

    return -1
}