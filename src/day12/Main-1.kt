package day12

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day12/example").inputStream()
    val lineList:MutableList<CharArray> = mutableListOf()

    inputStream.bufferedReader().forEachLine { line ->
        lineList += "1${line}1".toCharArray()
    }

    lineList.add(0, "1".repeat(lineList[0].size).toCharArray())
    lineList.add("1".repeat(lineList[0].size).toCharArray())

    println(lineList.forEach { println(it) })

    println(findNeighbours(1, 1, 'A', Pair(0, 0), lineList))
}

fun findNeighbours(x: Int, y:Int, symbol: Char, areaPerimeter: Pair<Int, Int>, list: MutableList<CharArray>): Pair<Int, Int>{
    var newAreaPerimeter = areaPerimeter

    if(list[y][x] == symbol){
        list[y][x] = '.'
        newAreaPerimeter = Pair(newAreaPerimeter.first + 1, newAreaPerimeter.second)
    }

    // left
    if(x - 1 >= 0 && list[y][x - 1] == symbol){
        val tmpAP = findNeighbours(x - 1, y, symbol, Pair(0, 0), list)
        newAreaPerimeter = Pair(newAreaPerimeter.first + tmpAP.first, newAreaPerimeter.second + tmpAP.second)
    } else if (x - 1 >= 0 && list[y][x - 1] != '.')
        newAreaPerimeter = Pair(newAreaPerimeter.first, newAreaPerimeter.second + 1)
    // right
    if(x + 1 < list[y].size && list[y][x + 1] == symbol){
        val tmpAP = findNeighbours(x + 1, y, symbol, Pair(0, 0), list)
        newAreaPerimeter = Pair(newAreaPerimeter.first + tmpAP.first, newAreaPerimeter.second + tmpAP.second)
    } else if (x + 1 < list[y].size && list[y][x + 1] != '.')
        newAreaPerimeter = Pair(newAreaPerimeter.first, newAreaPerimeter.second + 1)
    // down
    if(y - 1 >= 0 && list[y - 1][x] == symbol){
        val tmpAP = findNeighbours(x, y - 1, symbol, Pair(0, 0), list)
        newAreaPerimeter = Pair(newAreaPerimeter.first + tmpAP.first, newAreaPerimeter.second + tmpAP.second)
    } else if (y - 1 >= 0 && list[y - 1][x] != '.')
        newAreaPerimeter = Pair(newAreaPerimeter.first, newAreaPerimeter.second + 1)
    // up
    if(y + 1 < list.size && list[y + 1][x] == symbol){
        val tmpAP = findNeighbours(x, y + 1, symbol, Pair(0, 0), list)
        newAreaPerimeter = Pair(newAreaPerimeter.first + tmpAP.first, newAreaPerimeter.second + tmpAP.second)
    } else if (y + 1 < list.size && list[y + 1][x] != '.')
        newAreaPerimeter = Pair(newAreaPerimeter.first, newAreaPerimeter.second + 1)

    return newAreaPerimeter
}
