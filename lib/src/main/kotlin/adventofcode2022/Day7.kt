package adventofcode2022

fun main() {
    val input = readFileText("/day7").trim()
    val cdChunks = input.split("$ cd ").drop(1)

    var currentPath: Directory? = null
    val allDirectories = mutableListOf<Directory>()
    for (cdChunk in cdChunks) {
        val name = cdChunk.lines().first()
        if (name == "..") {
            currentPath = currentPath?.parent
        } else {
            val files = cdChunk.lines().filter { it.isNotEmpty() && it.first().isDigit() }
            val directory = Directory(
                name = name,
                parent = currentPath,
                files = files.toMutableSet()
            )
            currentPath = directory
            updateParentSizes(directory)
            allDirectories.add(directory)
        }
    }
    val targetSpace = 30000000 - (70000000 - allDirectories[0].files.toFileSize())

    val minimumSize = allDirectories.map {
        it.files.toFileSize()
    }.filter { it > targetSpace }.minOf { it }
    println("$minimumSize")



}

fun Set<String>.toFileSize(): Int {
    return this.sumOf { it.split(" ").first().toInt() }
}

fun updateParentSizes(directory: Directory) {
    var parent = directory.parent
    while(parent != null) {
        parent.files.addAll(directory.files)
        parent = parent.parent
    }
}

data class Directory(val name: String, val parent: Directory?, val files: MutableSet<String>)



