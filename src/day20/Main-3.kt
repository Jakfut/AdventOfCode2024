package day20

import _template.*
import java.io.File
import java.io.InputStream
import java.util.PriorityQueue

const val MIN_CHEATING_VALUE = 100
const val MAX_CHEAT_LENGTH = 20

fun main(){
    val inputStream: InputStream = File("src/day20/in").inputStream()
    val lineList = mutableListOf<String>()
    var cheatCount = 0L

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val grid: MutableGrid = lineList.map { it.toCharArray().toList() }.toList().toMutableGrid()

    val distances = findShortestCostV2(grid, findCharInGrid(grid, 'S'), findCharInGrid(grid, 'E'))
    val distancesCopy = deepCopyDistances(distances)

    distances.forEach { t, u ->
        distancesCopy.forEach {
            val distance = it.key.distanceTo(t)
            if (distance <= MAX_CHEAT_LENGTH){
                val value = it.value - u - distance
                if (value >= MIN_CHEATING_VALUE){
                    cheatCount++
                }
            }
        }

        distancesCopy.remove(t)
    }

    println(cheatCount)
}

fun deepCopyDistances(original: MutableMap<Vec2, Long>): MutableMap<Vec2, Long> {
    val newMap = mutableMapOf<Vec2, Long>()
    for ((key, value) in original) {
        // Create a new Vec2 using copy()
        newMap[key.copy()] = value
    }
    return newMap
}