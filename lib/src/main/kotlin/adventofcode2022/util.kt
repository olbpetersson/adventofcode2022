package adventofcode2022

import java.io.File

fun readFile(path: String): File =
    File(object {}.javaClass.getResource(path).file)
