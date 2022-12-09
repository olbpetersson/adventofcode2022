package adventofcode2022

import adventofcode2022.Direction.*

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

data class Tree(
    val name: String,
    var height: Int = -1,
    var parent: Tree? = null,
    var right: Tree? = null,
    var bottom: Tree? = null,
    var left: Tree? = null
)

fun main() {

    val lines = readFileText("/day8").trim().lines()

    val treeMap = mutableMapOf<String, Tree>()
    lines.forEachIndexed { lineIndex, line ->
        line.forEachIndexed { charIndex, char ->
            val parentName = "${lineIndex - 1}${charIndex}"
            val rightName = "${lineIndex}${charIndex + 1}"
            val bottomName = "${lineIndex + 1}${charIndex}"
            val leftName = "${lineIndex}${charIndex - 1}"
            val value = Character.getNumericValue(char)
            val parentTree = if (lineIndex > 0) {
                treeMap.computeIfAbsent(parentName) { Tree(parentName) }
            } else null
            val rightTree =
                if (charIndex < line.length - 1) treeMap.computeIfAbsent(rightName) { Tree(rightName) } else null

            val bottomTree =
                if (lineIndex < lines.size - 1) treeMap.computeIfAbsent(bottomName) { Tree(bottomName) } else null
            val leftTree = if (charIndex > 0) treeMap.computeIfAbsent(leftName) { Tree(leftName) } else null

            val name = "$lineIndex$charIndex"
            val currentTree = treeMap.computeIfAbsent(name) {
                Tree(name)
            }

            currentTree.height = value
            currentTree.parent = parentTree
            currentTree.right = rightTree
            currentTree.bottom = bottomTree
            currentTree.left = leftTree
        }
    }
    val treesWithClearView = treeMap.values.filter { tree ->
        values().any { direction ->
            val hasClearView = hasClearView(direction, tree.height, tree)
            hasClearView
        }
    }

    println("${treesWithClearView.count()}")

}


fun hasClearView(direction: Direction, heightToBeat: Int, tree: Tree): Boolean {
    val treeInDirection = when (direction) {
        UP -> tree.parent
        RIGHT -> tree.right
        DOWN -> tree.bottom
        LEFT -> tree.left
    } ?: return true
    return (heightToBeat > (treeInDirection.height)) && hasClearView(
        direction,
        heightToBeat,
        treeInDirection
    )
}
