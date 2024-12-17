package day15

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day15/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val grid:MutableList<CharArray> = mutableListOf()
    val instructions = mutableListOf<Char>()
    var isGrid = true
    var score = 0


    lineList.forEach {
        if (it == "") {
            isGrid = false
            return@forEach
        }
        if (isGrid)
            grid.add(it.toCharArray())
        else
            instructions += it.map { it }
    }

    instructions.forEach {
        tick(it, grid)

        /*println("Instruction: $it")
        grid.forEach {
            println(it.joinToString(""))
        }*/
    }

    grid.forEachIndexed { index, chars ->
        chars.forEachIndexed { i, c ->
            if (c == 'O'){
                score += (100 * index + i)
            }
        }
    }
    println(score)
}

// checks whether a '#' or '.' comes first
fun checkAllowed(string: String) : Boolean {
    if (string.indexOf('.') == -1){
        return false
    }
    return string.indexOf('#') > string.indexOf('.')
}

fun tick(instruction: Char, grid:MutableList<CharArray>){
    when (instruction) {
        '>' -> {
            moveRight(grid)
        }
        '<' -> {
            rotateGrid90Degrees(grid)
            rotateGrid90Degrees(grid)
            moveRight(grid)
            rotateGrid90Degrees(grid)
            rotateGrid90Degrees(grid)
        }
        '^' -> {
            rotateGrid90Degrees(grid)
            moveRight(grid)
            rotateGrid90Degrees(grid)
            rotateGrid90Degrees(grid)
            rotateGrid90Degrees(grid)
        }
        'v' -> {
            rotateGrid90Degrees(grid)
            rotateGrid90Degrees(grid)
            rotateGrid90Degrees(grid)
            moveRight(grid)
            rotateGrid90Degrees(grid)
        }
    }
}

fun moveRight(grid: MutableList<CharArray>){
    val playerPosition = getPlayerPosition(grid)

    val row = grid[playerPosition.first].joinToString("").substring(playerPosition.second)
    if (checkAllowed(row)){
        grid[playerPosition.first][playerPosition.second + row.indexOfFirst { it == '.' }] = grid[playerPosition.first][playerPosition.second + 1]
        grid[playerPosition.first][playerPosition.second + 1] = '@'
        grid[playerPosition.first][playerPosition.second] = '.'
    }
}

fun getPlayerPosition(grid: MutableList<CharArray>) : Pair<Int, Int>{
    for (i in grid.indices){
        for (j in grid[i].indices){
            if (grid[i][j] == '@'){
                return Pair(i, j)
            }
        }
    }
    return Pair(-1, -1)
}

fun rotateGrid90Degrees(grid: MutableList<CharArray>) {
    if (grid.isEmpty() || grid[0].isEmpty()) {
        return // Do nothing for empty or invalid input
    }

    val n = grid.size // Assuming a square grid for in-place rotation

    // 1. Transpose the matrix
    for (i in 0 until n) {
        for (j in i until n) {
            val temp = grid[i][j]
            grid[i][j] = grid[j][i]
            grid[j][i] = temp
        }
    }

    // 2. Reverse each row
    for (row in grid) {
        row.reverse()
    }
}