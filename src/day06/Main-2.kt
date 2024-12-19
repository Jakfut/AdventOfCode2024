package day06

import _template.*
import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day06/in").inputStream()
    val lineList = mutableListOf<String>()
    var origin = Vec2(0, 0)
    var currentPos = Vec2(0, 0)
    var currentDir = Vec2.UP
    val loopTiles = mutableSetOf<Vec2>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val grid:MutableGrid = parseGrid(lineList.joinToString("\n")).toMutableGrid()

    currentPos = findCharInGrid(grid, '^')
    origin = currentPos

    while (true){
        val nextPos = currentPos + currentDir

        if (!grid.inGrid(nextPos)) {
            break
        }
        if (grid[nextPos.row][nextPos.col] == WALL){
            currentDir = currentDir.turnRight()
        } else {
            currentPos = nextPos
        }

        if (nextPos != origin){
            val newGrid = grid.copy().toMutableGrid()
            newGrid[nextPos.row][nextPos.col] = '#'
            if (isLoop(newGrid)){
                loopTiles.add(nextPos)
            }
        }
    }

    println(loopTiles.size)
}

fun isLoop(grid: MutableGrid): Boolean {
    val visited = mutableSetOf<Pair<Vec2, Vec2>>()
    var currentPos = findCharInGrid(grid, '^')
    var currentDir = Vec2.UP

    while (true){
        val nextPos = currentPos + currentDir

        if (!grid.inGrid(nextPos)) {
            break
        }
        if (grid[nextPos.row][nextPos.col] == WALL){
            currentDir = currentDir.turnRight()
        } else {
            currentPos = nextPos
        }

        if (visited.contains(Pair(currentPos, currentDir))){
            return true
        }
        visited.add( Pair(currentPos, currentDir) )
    }

    return false
}