package adventofcode2022

import java.lang.IllegalStateException
import kotlin.math.sign

data class Coordinate(var x: Int = 0, var y: Int = 0)
class Day9(val lines: List<String>) {
    private val tailsVisited = mutableSetOf<Coordinate>()

    fun partOne() {
        var headPosition = Coordinate(0, 0)
        var tailPosition = Coordinate(0, 0)
        tailsVisited.add(tailPosition)
        lines.forEach {
            val (direction, value) = it.split(" ")
            repeat(value.toInt()) {
                val tempHead = headPosition.copy()
                when (direction) {
                    "R" -> headPosition.x++
                    "L" -> headPosition.x--
                    "U" -> headPosition.y++
                    "D" -> headPosition.y--
                }
                if (tailShouldMove(headPosition, tailPosition)) {
                    tailPosition = tempHead
                    tailsVisited.add(tailPosition)
                }
            }
        }
        println(tailsVisited.size)
    }

    fun partTwo() {
        val connectedHeadTailList = mutableListOf<Coordinate>()
        connectedHeadTailList.addAll((1..10).map { Coordinate() })
        lines.forEach { input ->
            val (direction, value) = input.split(" ")
            // Move head first
            // then check the rest as head-tail pair
            // IF tail should move, move it
            // add last tailS
            repeat(value.toInt()) {
                when (direction) {
                    "R" -> ++connectedHeadTailList.first().x
                    "L" -> --connectedHeadTailList.first().x
                    "U" -> ++connectedHeadTailList.first().y
                    "D" -> --connectedHeadTailList.first().y
                    else -> throw IllegalStateException()
                }
                connectedHeadTailList.subList(0, connectedHeadTailList.size).windowed(2)
                    .forEach { (currentHead, currentTail) ->
                        if (tailShouldMove(currentHead, currentTail)) {
                            currentTail.x += (currentHead.x - currentTail.x).sign
                            currentTail.y += (currentHead.y - currentTail.y).sign
                        }
                    }
                tailsVisited.add(connectedHeadTailList.last().copy())
            }
        }
        println(tailsVisited.size)
    }

}

private fun tailShouldMove(headPosition: Coordinate, tailPosition: Coordinate): Boolean {
    val horisontalDifference = Math.abs(headPosition.x - tailPosition.x)
    val verticalDifference = Math.abs(headPosition.y - tailPosition.y)

    return (horisontalDifference > 1 || verticalDifference > 1)
}


fun main() {
    Day9(readFileText("/day9").lines()).partTwo()
}
