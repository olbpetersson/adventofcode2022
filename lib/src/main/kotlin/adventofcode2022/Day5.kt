/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package adventofcode2022


fun main() {
    val lines = readFile("/day5").readLines()
    val (moveIndex, stacks) = getMoveIndexAndStacks(lines)
    lines.subList(moveIndex, lines.size).forEach { strategyLine ->
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

private fun getMoveIndexAndStacks(lines: List<String>): Pair<Int, MutableList<ArrayDeque<String>>> {
    val dequeList = mutableListOf<ArrayDeque<String>>()
    var moveIndex = 0
    while (!lines[moveIndex].startsWith("move")) {
        val line = lines[moveIndex]
        if (line.isNotEmpty()) {
            val columnsIncludingWhitespace = line.chunked(4)
            columnsIncludingWhitespace.forEachIndexed { columnIndex, column ->
                if (columnIndex >= dequeList.size) {
                    dequeList.add(ArrayDeque())
                }
                dequeList[columnIndex].add(column)
            }
        }
        moveIndex++
    }
    val cleanedDequeList = dequeList.map { it ->
        ArrayDeque(it.filter { it.isNotBlank() && !it.trim().first().isDigit() })
    } as ArrayList
    return moveIndex to cleanedDequeList
}
