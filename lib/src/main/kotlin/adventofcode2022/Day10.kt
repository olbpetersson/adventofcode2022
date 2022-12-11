package adventofcode2022

import java.lang.IllegalArgumentException

sealed class Instructions(val cycles: Int, open val value: Int = 0)
data class Add(override val value: Int) : Instructions(2)
data class Noop(override val value: Int = 0) : Instructions(1)

fun main() {
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

