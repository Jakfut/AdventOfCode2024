package day20

import _template.*
import java.io.File
import java.io.InputStream
import java.util.PriorityQueue

const val WALL = '#'
val DIRECTIONS = listOf(Vec2.UP, Vec2.DOWN, Vec2.LEFT, Vec2.RIGHT)

fun main(){
    val inputStream: InputStream = File("src/day20/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val grid: MutableGrid = lineList.map { it.toCharArray().toList() }.toList().toMutableGrid()

    val baseCost = findShortestCost(grid, findCharInGrid(grid, 'S'), findCharInGrid(grid, 'E'))
    var bigDeviation = 0

    grid.forEachIndexed { index, chars ->
        chars.forEachIndexed { i, c ->
            if (grid[index][i] == '#'){
                grid[index][i] = '.'

                val deviation = baseCost -findShortestCost(grid, findCharInGrid(grid, 'S'), findCharInGrid(grid, 'E'))

                grid[index][i] = '#'

                if (deviation >= 100){
                    bigDeviation++
                }
            }
        }
    }

    println("Big deviation: $bigDeviation")
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