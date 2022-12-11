package adventofcode2022

class Monkey(lines: List<String>) {
    val name: String
    val items: MutableList<Int> = mutableListOf()
    val testDivisor: Int

    /*
        Monkey 0:
      Starting items: 79, 98
      Operation: new = old * 19
      Test: divisible by 23
        If true: throw to monkey 2
        If false: throw to monkey 3
     */
    val neighbourMonkeyIds: Pair<String, String>
    val operationFunction: (Int) -> Int
    var inspectionCount: Int = 0
        private set


    init {
        val rawName = lines.first()
        val (itemLine, operationLine, testDivisorLine, monkeyTrueId, monkeyFalseId) = lines.subList(1, lines.size)
        this.name = rawName.substring(rawName.length - 2, rawName.length - 1)
        this.items.addAll(itemLine.substringAfter(":").split(",").map { it.trim().toInt() })
        this.testDivisor = testDivisorLine.split(" ").last().toInt()
        this.neighbourMonkeyIds = monkeyTrueId.split(" ").last() to monkeyFalseId.split(" ")
            .last()
        val operationString = operationLine.substringAfter("= ").split(" ")

        this.operationFunction = { other ->
            val (rawFirst, rawOperation, rawSecond) = operationString
            val firstArgument = rawFirst.toArg(other)
            val secondArgument = rawSecond.toArg(other)
            val operand: (Int, Int) -> Int = when {
                rawOperation.contains("*") -> { a, b -> a * b }
                else -> { a, b -> a + b }
            }
            operand(firstArgument, secondArgument)
        }
    }

    private fun String.toArg(param: Int): Int {
        return when {
            this.contains("old") -> param
            else -> this.trim().toInt()
        }
    }

    override fun toString(): String {
        return "Monkey(name='$name', items=$items, testDivisor=$testDivisor, neighbourMonkeyIds=$neighbourMonkeyIds, operationFunction=$operationFunction)"
    }

    fun inspectAndGetMonkeyIdToItemLevel(): List<Pair<String, Int>> {
        val monkeyMaps = items.map {
            val newWorryLevel = operationFunction(it) / 3
            if (newWorryLevel % testDivisor == 0) {
                neighbourMonkeyIds.first to newWorryLevel
            } else {
                neighbourMonkeyIds.second to newWorryLevel
            }
        }
        items.clear()
        inspectionCount += monkeyMaps.size
        return monkeyMaps
    }


}

fun main() {
    val input = readFileText("/day11")
    val monkeyMap = input.split("\n\n").map { Monkey(it.lines()) }.groupBy { it.name }
        .mapValues { entry -> entry.value.first() }
    repeat(20) {
        monkeyMap.values.forEach { monkey ->
            val monkeyList = monkey.inspectAndGetMonkeyIdToItemLevel()
            monkeyList.forEach { monkeyIdAndItem ->
                monkeyMap[monkeyIdAndItem.first]!!.items.add(monkeyIdAndItem.second)
            }
        }
    }

    val monkeyBusiness = monkeyMap.values
        .sortedByDescending { it.inspectionCount }
        .take(2)
        .map { it.inspectionCount }
        .reduce(Int::times)
    println("${monkeyBusiness}")
}
