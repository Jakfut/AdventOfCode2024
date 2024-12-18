package day16

import _template.Grid
import _template.Vec2
import _template.findCharInGrid
import java.io.File
import java.io.InputStream
import java.util.*

fun main(){
    val inputStream: InputStream = File("src/day16/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val grid: Grid = lineList.map { it.toCharArray().toList() }.toList()

    grid.forEach { println(it) }

    println(shortestPathTiles(grid, findCharInGrid(grid, 'S'), findCharInGrid(grid, 'E')))
}


fun shortestPathTiles(grid: Grid, start: Vec2, end: Vec2) : Int{
    val distances = mutableMapOf<Pair<Vec2, Vec2>, Long>()
    val queue = PriorityQueue<Pair<Vec2, Vec2>>(compareBy { t -> distances.getOrElse(t) { Long.MAX_VALUE } })
    var parents = mutableMapOf<Pair<Vec2, Vec2>, MutableList<Pair<Vec2, Vec2>>>()

    queue.add(Pair(start, Vec2.RIGHT))
    distances[Pair(start, Vec2.RIGHT)] = 0
    parents[Pair(start, Vec2.RIGHT)] = mutableListOf(queue.peek())


    while (queue.isNotEmpty()){
        val current = queue.poll()

        if (current.first == end){
            break
        }

        val neighbors = findNeighbors(grid, current)

        for (neighbor in neighbors.keys){
            val newDistance = distances[current]!! + neighbors[neighbor]!!

            if (newDistance < distances.getOrElse(neighbor) { Long.MAX_VALUE }){
                distances[neighbor] = newDistance
                queue.add(neighbor)

                parents[neighbor] = mutableListOf(current)
            }
            if(distances[neighbor]!! == newDistance){
                parents[neighbor]!!.add(current)
            }
        }
    }

    parents = parents.mapValues {
        it.value.groupBy { it.second }
            .map {
                it.value.first()
            }.toMutableList()
    }.toMutableMap()

    val tiles:MutableSet<Vec2> = mutableSetOf()
    val currentParents = parents[Pair(end, Vec2.UP)]!!

    while (currentParents.isNotEmpty()){
        if (currentParents.first().first == start){
            currentParents.remove(currentParents.first())
            continue
        }

        val current = currentParents.first()
        currentParents.remove(current)

        tiles.add(current.first)
        currentParents.addAll(parents[current] ?: mutableListOf())
    }

    return tiles.size + 2
}
