package day21

import _template.*
import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day21/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val instructions:MutableList<CharArray> = mutableListOf()
    var score = 0L

    lineList.forEach {
        instructions.add(it.toCharArray())
    }

    val numPad: Grid = listOf(
        listOf('7', '8', '9'),
        listOf('4', '5', '6'),
        listOf('1', '2', '3'),
        listOf('X', '0', 'A')
    )

    val directionalPad: Grid = listOf(
        listOf('X', '^', 'A'),
        listOf('<', 'v', '>'),
    )

    val keypads:MutableList<Keypad> = mutableListOf()
    for (i in 0..2)
        keypads.add(Keypad(Vec2(2, 0), directionalPad, keypads.lastOrNull()))

    keypads.add(Keypad(Vec2(2, 3), numPad, keypads.lastOrNull()))

    instructions.forEach {
        var shortestPath = ""
        it.forEach {
            shortestPath += getPath(keypads[keypads.size - 1], it)
            keypads[keypads.size - 1].currentPos = Vec2(findCharInGrid(numPad, it).x, findCharInGrid(numPad, it).y)
        }
        score += shortestPath.length * it.joinToString("").dropLast(1).toLong()
    }

    println(score)
}

fun getPath(keypad: Keypad, instruction: Char) : String {
    if (keypad.nextKeypad == null)
        return instruction.toString()

    val paths = getPaths(keypad, instruction)
    val cheaperPath = getCheaperPath(keypad, paths)

    val solutions = mutableListOf<String>()
    cheaperPath.forEach {
        solutions.add(getPath(keypad.nextKeypad, it))
        keypad.nextKeypad.currentPos = findCharInGrid(keypad.nextKeypad.grid, it)
    }

    return solutions.joinToString("")
}

fun getPaths(keypad: Keypad, instruction: Char): Pair<String, String> {
    var x = ""
    var y = ""
    val delta = findCharInGrid(keypad.grid, instruction) - keypad.currentPos
    var paths = Pair("", "")

    if (delta.x < 0)
        x = "<".repeat(-delta.x)
    else
        x = ">".repeat(delta.x)
    if (delta.y < 0)
        y = "^".repeat(-delta.y)
    else
        y = "v".repeat(delta.y)

    if (isValidHorizontalFirst(keypad.currentPos, delta, keypad.invalidPosition)) {
        paths = Pair(x + y + "A", "")
    }

    if (isValidVerticalFirst(keypad.currentPos, delta, keypad.invalidPosition)) {
        paths = Pair(paths.first, y + x + "A")
    }

    if (paths.first == paths.second)
        return Pair(paths.first, "")

    return paths
}

fun isValidHorizontalFirst(currentPos: Vec2, delta: Vec2, invalidPosition: Vec2): Boolean {
    for (i in 1..kotlin.math.abs(delta.x)) {
        val tempX = if (delta.x > 0) currentPos.x + i else currentPos.x - i
        if (tempX == invalidPosition.x && currentPos.y == invalidPosition.y) {
            return false
        }
    }

    return true
}

fun isValidVerticalFirst(currentPos: Vec2, delta: Vec2, invalidPosition: Vec2): Boolean {
    for (i in 1..kotlin.math.abs(delta.y)) {
        val tempY = if (delta.y > 0) currentPos.y + i else currentPos.y - i
        if (currentPos.x == invalidPosition.x && tempY == invalidPosition.y) {
            return false
        }
    }

    return true
}

fun getCheaperPath(keypad: Keypad, paths: Pair<String, String>): String {
    if (paths.second == "")
        return paths.first
    if (paths.first == "")
        return paths.second

    val instructions = paths.toList().joinToString("").toCharArray().distinct().toCharArray()

    instructions.forEach {
        if (!isCharInGrid(keypad.grid, it))
            if (it == '<')
                return paths.first
            else
                return paths.second
    }

    var cost1 = 0

    instructions.forEach {
        cost1 += findCharInGrid(keypad.grid, it).distanceTo(keypad.currentPos)
    }

    val tmp = instructions[2]
    instructions[1] = instructions[2]
    instructions[2] = tmp

    var cost2 = 0

    instructions.forEach {
        cost2 += findCharInGrid(keypad.grid, it).distanceTo(keypad.currentPos)
    }

    if (cost1 < cost2)
        return paths.first
    else if (cost2 < cost1)
        return paths.second

    if (keypad.nextKeypad == null)
        return paths.first

    return getCheaperPath(keypad.nextKeypad, paths)
}

data class Keypad(
    var currentPos: Vec2,
    val grid: Grid,
    val nextKeypad: Keypad?,
    val invalidPosition: Vec2 = findCharInGrid(grid, 'X'),
    val charPositions: MutableMap<Char, Vec2> = mutableMapOf()
) {
    init {
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, char ->
                charPositions[char] = Vec2(x, y)
            }
        }
    }
}