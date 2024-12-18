package day16

import _template.*
import java.io.File
import java.io.InputStream
import java.util.PriorityQueue

const val WALL = '#'

fun main(){
    val inputStream: InputStream = File("src/day16/example2").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val grid: Grid = lineList.map { it.toCharArray().toList() }.toList()

    grid.forEach { println(it) }

    println(findShortestCost(grid, findCharInGrid(grid, 'S'), findCharInGrid(grid, 'E')))
}

fun findNeighbors(grid: Grid, cur: Pair<Vec2, Vec2>) : Map<Pair<Vec2, Vec2>, Long>{
    val neighbors = mutableMapOf<Pair<Vec2, Vec2>, Long>()

    if (grid.inGrid(cur.first + cur.second) && grid.at(cur.first + cur.second) != WALL){
        neighbors[Pair(cur.first + cur.second, cur.second)] = 1
    }

    val left = Pair(cur.first, cur.second.turnLeft())
    val right = Pair(cur.first, cur.second.turnRight())

    if (grid.inGrid(left.first) && grid.at(left.first) != WALL){
        neighbors[left] = 1000
    }

    if (grid.inGrid(right.first) && grid.at(right.first) != WALL){
        neighbors[right] = 1000
    }

    return neighbors
}

fun findShortestCost(grid: Grid, start: Vec2, end: Vec2) : Int{
    val distances = mutableMapOf<Pair<Vec2, Vec2>, Long>()
    val queue = PriorityQueue<Pair<Vec2, Vec2>>(compareBy { t -> distances.getOrElse(t) { Long.MAX_VALUE } })

    queue.add(Pair(start, Vec2.RIGHT))
    distances[Pair(start, Vec2.RIGHT)] = 0


    while (queue.isNotEmpty()){
        val current = queue.poll()

        if (current.first == end){
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