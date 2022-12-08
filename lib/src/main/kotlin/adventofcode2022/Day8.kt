package adventofcode2022

fun main() {

    val lines = readFileText("/day8").lines()

    val matrix = lines.map { line -> line.map { it.digitToInt() } }

    val edges = matrix[0].size * 2 + matrix[1].size * 2 - 4

    val treeCount = (1 until matrix.size - 1).sumOf { rowIndex ->
        val currentRow = matrix[rowIndex]
        (1 until currentRow.size - 1).count { columnIndex ->
            val currentColumn = currentRow[columnIndex]
            val upwardsTrees = matrix.filterIndexed { index, _ -> index < rowIndex }.map { it[columnIndex] }
            val downWardsTrees = matrix.filterIndexed { index, _ -> index > rowIndex }.map { it[columnIndex] }
            val leftTrees = currentRow.subList(0, columnIndex)
            val rightTrees = currentRow.subList(columnIndex + 1, currentRow.size)
            (leftTrees.none { it >= currentColumn } ||
                rightTrees.none { it >= currentColumn } ||
                downWardsTrees.none { it >= currentColumn } ||
                upwardsTrees.none { it >= currentColumn }
                )
        }
    }
    println(edges + treeCount)
}
