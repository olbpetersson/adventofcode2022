package adventofcode2022

import java.io.File

fun readFile(path: String): File =
    File(object {}.javaClass.getResource(path).file)

fun readFileText(path: String): String =
    object {}.javaClass.getResource(path).readText().trim()
