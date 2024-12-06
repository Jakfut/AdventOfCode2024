package day02

import java.io.File
import java.io.InputStream
import kotlin.math.abs

fun main(){
    val inputStream: InputStream = File("src/day02/example").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    var score = 0

    lineList.forEach {
        val report: MutableList<Int> = it.split(" ").map { it.toInt() }.toMutableList()

        if (checkForAscending(report, 0)) {
            score++
        } else if (checkForDescending(report, 0)) {
            score++
        }
    }

    println(score)
}

fun checkForAscending(report: MutableList<Int>, depth: Int): Boolean {
    var depth = depth
    for (i in 1..report.lastIndex) {
        // check for descending
        if (report[i] < report[i-1]){
            if (depth == 0) {
                var newReport = report.toMutableList()
                newReport.removeAt(i)
                if (checkForAscending(newReport, 1)) return true

                newReport = report.toMutableList()
                newReport.removeAt(i-1)
                return (checkForAscending(newReport, 1))
            } else {
                return false
            }
        }
        // check for difference greater than 3
        if (abs(report[i-1] - report[i]) > 3) {
            if (depth == 0) {
                var newReport = report.toMutableList()
                newReport.removeAt(i)
                if (checkForAscending(newReport, 1)) return true

                newReport = report.toMutableList()
                newReport.removeAt(i-1)
                return (checkForAscending(newReport, 1))
            } else {
                return false
            }
        }
        // check for duplicates
        if (report[i-1] == report[i]) {
            if (depth == 0) {
                var newReport = report.toMutableList()
                newReport.removeAt(i)
                if (checkForAscending(newReport, 1)) return true

                newReport = report.toMutableList()
                newReport.removeAt(i-1)
                return (checkForAscending(newReport, 1))
            } else {
                return false
            }
        }
        if (i == report.lastIndex) return true
    }
    return false
}

fun checkForDescending(report: MutableList<Int>, depth:Int): Boolean {
    var depth = depth
    for (i in 1..report.lastIndex) {
        // check for ascending
        if (report[i] > report[i-1]){
            if (depth == 0) {
                var newReport = report.toMutableList()
                newReport.removeAt(i)
                if (checkForDescending(newReport, 1)) return true

                newReport = report.toMutableList()
                newReport.removeAt(i-1)
                return (checkForDescending(newReport, 1))
            } else {
                return false
            }
        }
        // check for difference greater than 3
        if (abs(report[i-1] - report[i]) > 3) {
            if (depth == 0) {
                var newReport = report.toMutableList()
                newReport.removeAt(i)
                if (checkForDescending(newReport, 1)) return true

                newReport = report.toMutableList()
                newReport.removeAt(i-1)
                return (checkForDescending(newReport, 1))
            } else {
                return false
            }
        }
        // check for duplicates
        if (report[i-1] == report[i]) {
            if (depth == 0) {
                var newReport = report.toMutableList()
                newReport.removeAt(i)
                if (checkForDescending(newReport, 1)) return true

                newReport = report.toMutableList()
                newReport.removeAt(i-1)
                return (checkForDescending(newReport, 1))
            } else {
                return false
            }
        }
        if (i == report.lastIndex) return true
    }
    return false
}