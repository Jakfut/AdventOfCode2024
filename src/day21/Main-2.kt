package day21

import _template.Grid
import _template.Vec2
import _template.findCharInGrid
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
            println(it)
            shortestPath += getPathV2(keypads[keypads.size - 1], it)
            keypads[keypads.size - 1].currentPos = Vec2(findCharInGrid(numPad, it).x, findCharInGrid(numPad, it).y)
        }
        score += shortestPath.length * it.joinToString("").dropLast(1).toLong()
    }

    println(score)
}

fun getPathV2(keypad: Keypad, instruction: Char) : String {
    if (keypad.nextKeypad == null)
        return instruction.toString()

    val paths = getPathsV2(keypad, instruction)
    val cheaperPath = getCheaperPath(keypad, paths)

    val solutions = mutableListOf<String>()
    cheaperPath.forEach {
        solutions.add(getPath(keypad.nextKeypad, it))
        keypad.nextKeypad.currentPos = findCharInGrid(keypad.nextKeypad.grid, it)
    }

    return solutions.joinToString("")
}

fun getPathsV2(keypad: Keypad, instruction: Char): Pair<String, String> {
    val currentPos = keypad.currentPos
    val targetPos = keypad.charPositions[instruction]!!
    val invalidPosition = keypad.charPositions['X']!!

    var x = ""
    var y = ""
    val delta = targetPos - currentPos

    var paths = Pair("", "")

    if (delta.x < 0)
        x = "<".repeat(-delta.x)
    else
        x = ">".repeat(delta.x)
    if (delta.y < 0)
        y = "^".repeat(-delta.y)
    else
        y = "v".repeat(delta.y)

    if (isValidHorizontalFirst(currentPos, delta, invalidPosition)) {
        paths = Pair(x + y + "A", "")
    }

    if (isValidVerticalFirst(currentPos, delta, invalidPosition)) {
        paths = Pair(paths.first, y + x + "A")
    }

    if (paths.first == paths.second) {
        return Pair(paths.first, "")
    }

    return paths
}