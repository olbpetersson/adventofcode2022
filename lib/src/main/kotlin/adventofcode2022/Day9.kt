package adventofcode2022

data class Coordinate(var x: Int, var y: Int)
class Day9(val lines: List<String>) {
    val tailsVisited = mutableSetOf<Coordinate>()

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
                if(tailShouldMove(headPosition, tailPosition)) {
                    tailPosition = tempHead
                    tailsVisited.add(tailPosition)
                }
            }
        }
        println(tailsVisited.size)
    }

    private fun tailShouldMove(headPosition: Coordinate, tailPosition: Coordinate): Boolean {
        val horisontalDifference = Math.abs(headPosition.x - tailPosition.x)
        val verticalDifference = Math.abs(headPosition.y - tailPosition.y)

        return headPosition != tailPosition && (horisontalDifference > 1 || verticalDifference > 1)
    }
}


fun main() {
    Day9(readFileText("/day9").lines()).partOne()
}
