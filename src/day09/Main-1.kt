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
        val diskMap:MutableList<String> = mutableListOf()

        denseDiskMap.forEachIndexed { index, list ->
            if (index % 2 == 0) {
                for (i in 0..<list.first().digitToInt()){
                    diskMap.add(idNumber.toString())
                }
                idNumber++
            } else {
                for (i in 0..<list.first().digitToInt()){
                    diskMap.add(".")
                }
            }
        }
        //println(diskMap)

        for (i in diskMap.indices){
            if (diskMap[i] == "."){
                for (j in diskMap.size-1 downTo 0){
                    if (j == i) break
                    if (diskMap[j] != "."){
                        diskMap[i] = diskMap[j]
                        diskMap[j] = "."
                        break
                    }
                }
            }
        }
        //println(diskMap)

        diskMap.forEachIndexed { index, s ->
            if (s != "."){
                score += index * s.toInt()
            }
        }
        println(score)
    }
}