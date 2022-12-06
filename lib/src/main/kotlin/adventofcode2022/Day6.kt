/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package adventofcode2022


fun main() {
    val input = readFileText("/day6").trim().replace("\n", "")
    var foundIndex = -1
    for (index in 0 until input.toList().size) {
        val sequenceSize = index + 14
        val sequence = input.substring(index, sequenceSize).toList()
        if (sequence.size == sequence.toSet().size) {
            foundIndex = sequenceSize
            break
        }
    }
    println(foundIndex)
}
