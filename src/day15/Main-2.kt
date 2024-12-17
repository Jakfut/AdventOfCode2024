package day15

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day15/example3").inputStream()
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

    grid.forEach { println(it.joinToString("")) }
    rotateGrid180Degrees(grid)
    grid.forEach { println(it.joinToString("")) }

    /*instructions.forEach {
        tick(it, grid)

        *//*println("Instruction: $it")
        grid.forEach {
            println(it.joinToString(""))
        }*//*
    }

    grid.forEachIndexed { index, chars ->
        chars.forEachIndexed { i, c ->
            if (c == 'O'){
                score += (100 * index + i)
            }
        }
    }
    println(score)*/
}

// checks whether a '#' or '.' comes first
fun checkAllowedv2(string: String) : Boolean {
    if (string.indexOf('.') == -1){
        return false
    }
    return string.indexOf('#') > string.indexOf('.')
}

fun tickv2(instruction: Char, grid:MutableList<CharArray>){
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

fun moveRightv2(grid: MutableList<CharArray>){
    val playerPosition = getPlayerPosition(grid)

    val row = grid[playerPosition.first].joinToString("").substring(playerPosition.second)
    if (checkAllowed(row)){
        grid[playerPosition.first][playerPosition.second + row.indexOfFirst { it == '.' }] = grid[playerPosition.first][playerPosition.second + 1]
        grid[playerPosition.first][playerPosition.second + 1] = '@'
        grid[playerPosition.first][playerPosition.second] = '.'
    }
}

fun rotateGrid180Degrees(grid: MutableList<CharArray>) {
    // 1. Reverse each row
    grid.forEachIndexed { index, chars ->
        grid[index] = chars.reversed().toCharArray()
    }

    // 2. Reverse the order of the rows
    grid.reverse()
}