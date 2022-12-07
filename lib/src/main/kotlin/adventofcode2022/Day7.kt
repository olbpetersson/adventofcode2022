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
    val sum = allDirectories.map {
        it.files.sumOf { it.split(" ").first().toInt() }
    }.filter { it < 100000 }.sum()
    println("$sum")
}

fun updateParentSizes(directory: Directory) {
    var parent = directory.parent
    while(parent != null) {
        parent.files.addAll(directory.files)
        parent = parent.parent
    }
}

data class Directory(val name: String, val parent: Directory?, val files: MutableSet<String>)



