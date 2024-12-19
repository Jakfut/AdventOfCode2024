package _template

import kotlin.math.*

typealias Grid = List<List<Char>>
typealias MutableGrid = MutableList<MutableList<Char>>

fun parseGrid(text: String): Grid =
    text.split("\n").filter { it.isNotEmpty() }.map { it.toList() }

fun findCharInGrid(grid: List<List<Char>>, char: Char): Vec2 {
    for ((rowIdx, row) in grid.withIndex()) {
        for ((colIdx, c) in row.withIndex()) {
            if (c == char) return Vec2(colIdx, rowIdx)
        }
    }
    error("Couldn't find char '$char' in grid")
}

fun Grid.toMutableGrid(): MutableGrid = this.map{ it.toMutableList() }.toMutableList()

fun Grid.print() {
    for (row in this) {
        for (cell in row) {
            print(cell)
        }
        println()
    }
}

fun Grid.copy(): Grid = this.map{ it.toMutableList() }.toMutableList()

fun toRadians(degrees: Double): Double = degrees * PI / 180.0

data class Vec2(val col: Int, val row: Int) {
    companion object {
        val LEFT = Vec2(-1, 0)
        val RIGHT = Vec2(1, 0)
        val UP = Vec2(0, -1)
        val DOWN = Vec2(0, 1)
        val UP_LEFT = Vec2(-1, -1)
        val UP_RIGHT = Vec2(1, -1)
        val DOWN_LEFT = Vec2(-1, 1)
        val DOWN_RIGHT = Vec2(1, 1)
    }

    val x = col
    val y = row

    operator fun plus(v: Vec2): Vec2 {
        return Vec2(this.col + v.col, this.row + v.row)
    }

    operator fun minus(v: Vec2): Vec2 {
        return Vec2(this.col - v.col, this.row - v.row)
    }

    operator fun times(scale: Int): Vec2 {
        return Vec2(this.col * scale, this.row * scale)
    }

    fun rotate(degrees: Double): Vec2 {

        val radians = toRadians(degrees)
        return Vec2(
            row = (sin(radians) * col + cos(radians) * row).roundToInt(),
            col = (cos(radians) * col - sin(radians) * row).roundToInt()
        )
    }

    fun turnRight(): Vec2 {
        return this.rotate(90.0)
    }

    fun turnLeft(): Vec2 {
        return this.rotate(-90.0)
    }

    fun turnAround(): Vec2 {
        return this.rotate(180.0)
    }
}

fun <T> List<List<T>>.inGrid(pos: Vec2): Boolean {
    if (pos.row in this.indices) {
        if (pos.col in this[pos.row].indices)
            return true
    }
    return false
}

fun <T> List<List<T>>.at(pos: Vec2): T {
    if (!this.inGrid(pos)) error("pos $pos outside of grid")

    return this[pos.row][pos.col]!!
}