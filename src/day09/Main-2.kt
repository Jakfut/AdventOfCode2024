package day09

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day09/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    var idNumber = 0
    var score = 0L

    lineList.forEach {
        val denseDiskMap:MutableList<String> = it.toMutableList().map { it.toString() }.toMutableList()
        var diskMap:MutableList<String> = mutableListOf()

        denseDiskMap.forEachIndexed { index, list ->
            if (index % 2 == 0) {
                var stringToAdd = ""
                for (i in 0..<list.first().digitToInt()){
                    stringToAdd += idNumber.toString() + "x"
                }
                diskMap.add(stringToAdd)
                idNumber++
            } else {
                if (list.first().digitToInt() != 0)
                    diskMap.add(".".repeat(list.first().digitToInt()))
            }
        }
        //println(diskMap)
        idNumber--

        var i = diskMap.size - 1
        while (i >= 0) {
            if (containsIdNumber(diskMap[i], idNumber)) {
                for (j in diskMap.indices){
                    if (j == i) break
                    if (diskMap[j].contains(".") && lengthWithoutX(diskMap[j], idNumber) >= lengthWithoutX(diskMap[i], idNumber)){
                        val lengthDifference = lengthWithoutX(diskMap[j], idNumber) - lengthWithoutX(diskMap[i],idNumber)
                        diskMap[j] = diskMap[i]
                        if (lengthDifference != 0){
                            diskMap.add(j + 1, ".".repeat(lengthDifference))
                            diskMap[i + 1] = ".".repeat(lengthWithoutX(diskMap[i + 1], idNumber))
                        } else {
                            diskMap[i] = ".".repeat(lengthWithoutX(diskMap[i], idNumber))
                        }
                        combinator(diskMap)
                        //println(diskMap)
                        i = diskMap.size
                        break
                    }
                }
                idNumber--
            }
            i--
        }

        //println(diskMap)

        i = diskMap.size - 1
        while (i >= 0) {
            if (!diskMap[i].contains(".")){
                if (diskMap[i].contains("x")){
                    val number = diskMap[i].split("x")[0].toInt()
                    val amount = lengthWithoutX(diskMap[i], number) - 1
                    diskMap.removeAt(i)
                    for (j in 0.. amount){
                        diskMap.add(i, number.toString())
                    }
                    i = diskMap.size
                }
            }
            i--
        }

        diskMap = diskMap.flatMap {
            if (it.all { ch -> ch == '.' }) it.map { "." } else listOf(it)
        }.toMutableList()

        //println(diskMap)

        diskMap.forEachIndexed { index, s ->
            if (s != "."){
                score += index * s.toInt()
            }
        }
        println(score)
    }
}

// combines elements like ., ..., ., to .....
fun combinator(diskMap: MutableList<String>) {
    var i = 1
    while (i < diskMap.size) {
        if (diskMap[i - 1].contains(".") && diskMap[i].contains(".")) {
            diskMap[i - 1] += diskMap[i]
            diskMap.removeAt(i)
        } else {
            i++
        }
    }
}

fun lengthWithoutX(string: String, idNumber: Int ): Int {
    if (string.contains(".")) return string.length
    return string.replace("x", "").length / idNumber.toString().length
}

fun containsIdNumber(string: String, idNumber: Int): Boolean {
    return string.split("x")[0] == idNumber.toString()
}