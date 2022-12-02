/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package adventofcode2022

fun main() {
    val input = readFile("/day1")
    val rawGroups = input.readText().split("\n\n")
    val max = rawGroups.maxOf { group ->
        group.trim().split("\n").map { it.toInt() }.sum()
    }
    println("max $max")
}
