package day20

import _template.*
import java.io.File
import java.io.InputStream
import java.util.PriorityQueue

const val MIN_CHEATING_VALUE = 76
const val MAX_CHEAT_LENGTH = 20

fun main(){
    val inputStream: InputStream = File("src/day20/example").inputStream()
    val lineList = mutableListOf<String>()
    var cheatCount = 0L

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val grid: MutableGrid = lineList.map { it.toCharArray().toList() }.toList().toMutableGrid()

    val distances = findShortestCostV2(grid, findCharInGrid(grid, 'S'), findCharInGrid(grid, 'E'))

    //println(distances.size)

    /*findAllCheats(grid, distances, findCharInGrid(grid, 'S')).forEach { (k, v) ->
        println("$k: $v")
    }

    println(findCharInGrid(grid, 'S'))
    println(distances[Vec2(1, 3)])
    println(distances[Vec2(3, 3)])*/

    /*distances.forEach { t, u ->
        findAllCheats(grid, distances, t).forEach { (k, v) ->
            if (v > cheats.getOrElse(t) { 0 })
                cheats[k] = v
        }
    }*/

    distances.forEach {
        cheatCount += findAllCheats(grid, distances, it.key)
    }

    println(cheatCount)
}

fun findAllNeighbors(grid: Grid, current: Vec2) : Map<Vec2, Long>{
    return DIRECTIONS.map { it + current }.associateWith { 1L }
}

fun findShortestCostV2(grid: Grid, start: Vec2, end: Vec2) : MutableMap<Vec2, Long> {
    val distances = mutableMapOf<Vec2, Long>()
    val queue = PriorityQueue<Vec2>(compareBy { t -> distances.getOrElse(t) { Long.MAX_VALUE } })

    distances[start] = 0
    queue.add(start)

    while (queue.isNotEmpty()){
        val current = queue.poll()

        val neighbors = findNeighbors(grid, current)

        for (neighbor in neighbors.keys){
            val newDistance = distances[current]!! + neighbors[neighbor]!!

            if (newDistance < distances.getOrElse(neighbor) { Long.MAX_VALUE }){
                distances[neighbor] = newDistance
                queue.add(neighbor)
            }
        }
    }

    return distances
}

fun findCheats(grid: Grid, current: Vec2, oldPath: MutableMap<Vec2, Long>, currentCost:Long, cheatLength: Long) : MutableSet<Vec2>{
    val neighbors = findNeighbors(grid, current)
    val cheatPositions = mutableSetOf<Vec2>()

    neighbors.forEach { (k, v) ->
        if (oldPath[k]!! - currentCost - cheatLength >= MIN_CHEATING_VALUE){
            //println("Seconds saved: ${abs(oldPath[k]!! - currentCost) - cheatLength}")
            cheatPositions.add(k)
        }
    }

    return cheatPositions
}

fun findAllCheats(grid: Grid, oldPath: MutableMap<Vec2, Long>, start: Vec2) : Int{
    val cheatPositions = mutableSetOf<Vec2>()
    val distances = mutableMapOf<Vec2, Long>()
    val queue = PriorityQueue<Vec2>(compareBy { t -> distances.getOrElse(t) { Long.MAX_VALUE } })

    queue.addAll(findAllNeighbors(grid, start).keys)
    distances.putAll(findAllNeighbors(grid, start).mapValues { 2L })
    cheatPositions += findCheats(grid, start, oldPath, oldPath[start]!!, 2)

    while (queue.isNotEmpty()){
        val current = queue.poll()
        val neighbors = findAllNeighbors(grid, current)

        for (neighbor in neighbors.keys){
            val newDistance = distances[current]!! + neighbors[neighbor]!!

            if (newDistance > MAX_CHEAT_LENGTH)
                continue

            cheatPositions += findCheats(grid, neighbor, oldPath, oldPath[start]!!, newDistance)

            if (newDistance < distances.getOrElse(neighbor) { Long.MAX_VALUE }){
                distances[neighbor] = newDistance
                queue.add(neighbor)
            }
        }
    }

    return cheatPositions.size
}