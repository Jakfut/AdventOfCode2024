package day23

import java.io.File
import java.io.InputStream

fun main(){
    val inputStream: InputStream = File("src/day23/in").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val nodes = mutableSetOf<Node>()

    lineList.forEach {
        val (from, to) = it.split("-")
        val fromNode = nodes.find { it.name == from } ?: Node(from).also { nodes.add(it) }
        val toNode = nodes.find { it.name == to } ?: Node(to).also { nodes.add(it) }
        fromNode.connections.add(toNode)
        toNode.connections.add(fromNode)
    }

    val maximumClique = findMaximumClique(nodes).toMutableList().sortedBy { it.name }.joinToString(separator = ",")
    println("Password: $maximumClique")
}

fun findMaximumClique(nodes: MutableSet<Node>): Set<Node> {
    var maxClique: Set<Node> = setOf()

    fun bronKerbosch(r: MutableSet<Node>, p: MutableSet<Node>, x: MutableSet<Node>) {
        if (p.isEmpty() && x.isEmpty()) {
            if (r.size > maxClique.size) {
                maxClique = r.toSet()
            }
            return
        }

        val pCopy = p.toSet()
        pCopy.forEach { v ->
            bronKerbosch(
                r.plus(v).toMutableSet(),
                p.intersect(v.connections).toMutableSet(),
                x.intersect(v.connections).toMutableSet()
            )
            p.remove(v)
            x.add(v)
        }
    }

    bronKerbosch(mutableSetOf(), nodes, mutableSetOf())
    return maxClique
}

data class Node(val name: String, val connections: MutableList<Node> = mutableListOf()){
    override fun toString(): String {
        return name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}