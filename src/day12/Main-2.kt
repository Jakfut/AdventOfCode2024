package day12

import java.io.File
import java.io.InputStream
import kotlin.math.abs

fun main(){
    val inputStream: InputStream = File("src/day12/in").inputStream()
    val lineList:MutableList<CharArray> = mutableListOf()

    inputStream.bufferedReader().forEachLine { line ->
        lineList += "1${line}1".toCharArray()
    }

    var score = 0

    lineList.add(0, "1".repeat(lineList[0].size).toCharArray())
    lineList.add("1".repeat(lineList[0].size).toCharArray())

    lineList.forEachIndexed { index, chars ->
        chars.forEachIndexed { i, c ->
            if (c != '1' && c == c.uppercaseChar()){
                val perimeter = Perimeter(mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())
                val areaPerimeter = findNeighboursV2(i, index, c, Pair(0, 0), perimeter, lineList)

                /*println("PerimeterUp: ${calcPerimeterHorizontal(
                    perimeter.up.sortedWith(
                        compareBy<Pair<Int, Int>> { it.first }.thenByDescending { it.second }
                    ).toMutableList(),
                    1)}"
                )
                println("PerimeterDown: ${calcPerimeterHorizontal(
                    perimeter.down.sortedWith(
                        compareBy<Pair<Int, Int>> { it.first }.thenByDescending { it.second }
                    ).toMutableList(),
                    1)}"
                )
                println("PerimeterLeft: ${calcPerimeterVertical(
                    perimeter.left.sortedWith(
                        compareBy<Pair<Int, Int>> { it.second }.thenByDescending { it.first }
                    ).toMutableList(),
                    1)}"
                )
                println("PerimeterRight: ${calcPerimeterVertical(
                    perimeter.right.sortedWith(
                        compareBy<Pair<Int, Int>> { it.second }.thenByDescending { it.first }
                    ).toMutableList(),
                    1)}"
                )*/

                var perimeterInt = calcPerimeterHorizontal(
                    perimeter.up.sortedWith(
                        compareBy<Pair<Int, Int>> { it.first }.thenByDescending { it.second }
                    ).toMutableList(),
                    1)
                perimeterInt += calcPerimeterHorizontal(
                    perimeter.down.sortedWith(
                        compareBy<Pair<Int, Int>> { it.first }.thenByDescending { it.second }
                    ).toMutableList(),
                    1)
                perimeterInt += calcPerimeterVertical(
                    perimeter.left.sortedWith(
                        compareBy<Pair<Int, Int>> { it.second }.thenByDescending { it.first }
                    ).toMutableList(),
                    1)
                perimeterInt += calcPerimeterVertical(
                    perimeter.right.sortedWith(
                        compareBy<Pair<Int, Int>> { it.second }.thenByDescending { it.first }
                    ).toMutableList(),
                    1)

                println("Symbol: $c Cost: ${areaPerimeter.first * perimeterInt} Area: ${areaPerimeter.first} Perimeter: $perimeterInt")
                score += areaPerimeter.first * perimeterInt
            }
        }
    }

    println(score)
}

fun findNeighboursV2(x: Int, y:Int, symbol: Char, areaPerimeter: Pair<Int, Int>, perimeter: Perimeter, list: MutableList<CharArray>): Pair<Int, Int>{
    var newAreaPerimeter = areaPerimeter

    if(list[y][x] == symbol){
        list[y][x] = symbol.lowercaseChar()
        newAreaPerimeter = Pair(newAreaPerimeter.first + 1, newAreaPerimeter.second)
    }
    
    // left
    if(x - 1 >= 0 && list[y][x - 1] == symbol){
        val tmpAP = findNeighboursV2(x - 1, y, symbol, Pair(0, 0), perimeter, list)
        newAreaPerimeter = Pair(newAreaPerimeter.first + tmpAP.first, newAreaPerimeter.second + tmpAP.second)
    } else if (x - 1 >= 0 && list[y][x - 1] != symbol.lowercaseChar()){
        newAreaPerimeter = Pair(newAreaPerimeter.first, newAreaPerimeter.second + 1)
        perimeter.left.add(Pair(y, x - 1))
    }
    // right
    if(x + 1 < list[y].size && list[y][x + 1] == symbol){
        val tmpAP = findNeighboursV2(x + 1, y, symbol, Pair(0, 0), perimeter, list)
        newAreaPerimeter = Pair(newAreaPerimeter.first + tmpAP.first, newAreaPerimeter.second + tmpAP.second)
    } else if (x + 1 < list[y].size && list[y][x + 1] != symbol.lowercaseChar()) {
        newAreaPerimeter = Pair(newAreaPerimeter.first, newAreaPerimeter.second + 1)
        perimeter.right.add(Pair(y, x + 1))
    }
    // down
    if(y - 1 >= 0 && list[y - 1][x] == symbol){
        val tmpAP = findNeighboursV2(x, y - 1, symbol, Pair(0, 0), perimeter, list)
        newAreaPerimeter = Pair(newAreaPerimeter.first + tmpAP.first, newAreaPerimeter.second + tmpAP.second)
    } else if (y - 1 >= 0 && list[y - 1][x] != symbol.lowercaseChar()) {
        newAreaPerimeter = Pair(newAreaPerimeter.first, newAreaPerimeter.second + 1)
        perimeter.down.add(Pair(y - 1, x))
    }
    // up
    if(y + 1 < list.size && list[y + 1][x] == symbol){
        val tmpAP = findNeighboursV2(x, y + 1, symbol, Pair(0, 0), perimeter, list)
        newAreaPerimeter = Pair(newAreaPerimeter.first + tmpAP.first, newAreaPerimeter.second + tmpAP.second)
    } else if (y + 1 < list.size && list[y + 1][x] != symbol.lowercaseChar()) {
        newAreaPerimeter = Pair(newAreaPerimeter.first, newAreaPerimeter.second + 1)
        perimeter.up.add(Pair(y + 1, x))
    }

    return newAreaPerimeter
}

data class Perimeter(
    val up: MutableList<Pair<Int, Int>>,
    val down: MutableList<Pair<Int, Int>>,
    val left: MutableList<Pair<Int, Int>>,
    val right: MutableList<Pair<Int, Int>>
)

fun calcPerimeterHorizontal(singlePerimeter: MutableList<Pair<Int, Int>>, perimeter: Int): Int{
    val i = 1
    while (i < singlePerimeter.size){
        if (singlePerimeter[i].first == singlePerimeter[0].first && abs(singlePerimeter[i].second - singlePerimeter[i - 1].second) == 1){
            singlePerimeter.removeAt(i - 1)
        } else {
            singlePerimeter.removeAt(i - 1)
            return calcPerimeterHorizontal(singlePerimeter, perimeter + 1)
        }
    }

    return perimeter
}

fun calcPerimeterVertical(singlePerimeter: MutableList<Pair<Int, Int>>, perimeter: Int): Int{
    val i = 1
    while (i < singlePerimeter.size){
        if (singlePerimeter[i].second == singlePerimeter[0].second && abs(singlePerimeter[i].first - singlePerimeter[i - 1].first) == 1){
            singlePerimeter.removeAt(i - 1)
        } else {
            singlePerimeter.removeAt(i - 1)
            return calcPerimeterVertical(singlePerimeter, perimeter + 1)
        }
    }

    return perimeter
}

