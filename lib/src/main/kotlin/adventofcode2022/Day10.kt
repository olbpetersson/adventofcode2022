package adventofcode2022

import java.lang.IllegalArgumentException

sealed class Instructions(val cycles: Int, open val value: Int = 0)
data class Add(override val value: Int) : Instructions(2)
data class Noop(override val value: Int = 0) : Instructions(1)

fun main() {
    partTwo()
}

fun partTwo() {
    val lines = readFileText("/day10").lines()
    val instructions: List<Instructions> = lines.map {
        val text = it.split(" ")
        when {
            text[0].startsWith("addx") -> Add(text[1].toInt())
            text[0].startsWith("noop") -> Noop()
            else -> throw IllegalArgumentException()
        }
    }
    var registerValue = 1
    var numberOfCycles = 0
    var cycleString = ""
    instructions.forEach { instruction ->
        repeat(instruction.cycles) {
            numberOfCycles++
            val normalizedCycle = numberOfCycles%40
            if(normalizedCycle-1 in (registerValue-1..registerValue+1)) {
                cycleString+="#"
            } else {
                cycleString+="."
            }
            if(normalizedCycle == 0) {
                println("$cycleString ${cycleString.length} at $numberOfCycles")
                cycleString = ""
            }

        }
        registerValue += instruction.value
    }
}
fun partOne() {
    val lines = readFileText("/day10").lines()
    val instructions: List<Instructions> = lines.map {
        val text = it.split(" ")
        when {
            text[0].startsWith("addx") -> Add(text[1].toInt())
            text[0].startsWith("noop") -> Noop()
            else -> throw IllegalArgumentException()
        }
    }
    var registerValue = 1
    var numberOfCycles = 0
    val divisibleByTwentyValues = mutableListOf<Int>()
    val listToLookFor = listOf(20, 60, 100, 140, 180, 220)
    instructions.forEach { instruction ->
        repeat(instruction.cycles) {
            numberOfCycles++
            if(listToLookFor.any { it == numberOfCycles }) {
                divisibleByTwentyValues.add(registerValue * numberOfCycles)
            }
        }
        registerValue += instruction.value
    }
    println("${divisibleByTwentyValues.sum()}")
}

