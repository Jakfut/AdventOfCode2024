package day06

import _template.*
import java.io.File
import java.io.InputStream

const val WALL = '#'

fun main(){
    val inputStream: InputStream = File("src/day06/in").inputStream()
    val lineList = mutableListOf<String>()
    var currentPos = Vec2(0, 0)
    var currentDir = Vec2.UP

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val grid:MutableGrid = parseGrid(lineList.joinToString("\n")).toMutableGrid()

    grid.print()
    println()

    currentPos = findCharInGrid(grid, '^')
    grid[currentPos.row][currentPos.col] = 'X'

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

        grid[currentPos.row][currentPos.col] = 'X'
    }

    grid.print()

    println(grid.sumOf { row -> row.count { it == 'X' } })
}