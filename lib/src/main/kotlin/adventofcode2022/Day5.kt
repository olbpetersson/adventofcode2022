/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package adventofcode2022


fun main() {
    val input = readFileText("/day5")
    val (stacksInput, strategyInput) = input.split("\n\n")
    val stacks = getStacks(stacksInput)
    strategyInput.trim().lines().forEach { strategyLine ->
        val instructions = strategyLine.split(" ")
        val quantity = instructions[1].toInt()
        val from = instructions[3].toInt() - 1
        val to = instructions[5].toInt() - 1

        val elements = (1..quantity).map {
            stacks[from].removeFirst()
        }.reversed()
        elements.forEach { element ->
            stacks[to].addFirst(element)
        }
    }
    println("${stacks.map { it.first() }}")

}

private fun getStacks(stacksInput: String): java.util.ArrayList<ArrayDeque<String>> {
    val dequeList = mutableListOf<ArrayDeque<String>>()
    stacksInput.lines().forEach { line ->
        if (line.isNotEmpty()) {
            val columnsIncludingWhitespace = line.chunked(4)
            columnsIncludingWhitespace.forEachIndexed { columnIndex, column ->
                if (columnIndex >= dequeList.size) {
                    dequeList.add(ArrayDeque())
                }
                dequeList[columnIndex].add(column)
            }
        }
    }
    val cleanedDequeList = dequeList.map { it ->
        ArrayDeque(it.filter { it.isNotBlank() && !it.trim().first().isDigit() })
    } as ArrayList
    return cleanedDequeList
}
