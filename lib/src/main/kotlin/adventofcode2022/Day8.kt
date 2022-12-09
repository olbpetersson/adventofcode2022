package adventofcode2022

fun main() {

    val lines = readFileText("/day8").lines()

    val matrix = lines.map { line -> line.map { it.digitToInt() } }

    val max = (1 until matrix.size - 1).flatMap { rowIndex ->
        val currentRow = matrix[rowIndex]
        return@flatMap (1 until currentRow.size - 1).map { columnIndex ->
            val currentColumn = currentRow[columnIndex]
            val upwardsTrees = matrix.filterIndexed { index, _ -> index < rowIndex }.map { it[columnIndex] }
            val downWardsTrees = matrix.filterIndexed { index, _ -> index > rowIndex }.map { it[columnIndex] }
            val leftTrees = currentRow.subList(0, columnIndex)
            val rightTrees = currentRow.subList(columnIndex + 1, currentRow.size)
            val leftTreesLesserThan = leftTrees.takeLastWhile { it < currentColumn }
            val rightTreesLesserThan = rightTrees.takeWhile { it < currentColumn }
            val downWardsLesserThan = downWardsTrees.takeWhile { it < currentColumn }
            val upwardsLesserThan = upwardsTrees.takeLastWhile { it < currentColumn }
            (leftTreesLesserThan.count() + (leftTrees - leftTreesLesserThan).size.coerceAtMost(1)) *
                (rightTreesLesserThan.count() + (rightTrees - rightTreesLesserThan).size.coerceAtMost(1)) *
                (downWardsLesserThan.count() + (downWardsTrees - downWardsLesserThan).size.coerceAtMost(1)) *
                (upwardsLesserThan.count() + (upwardsTrees - upwardsLesserThan).size.coerceAtMost(1))
        }
    }.maxOf { it }
    println(max)
}
