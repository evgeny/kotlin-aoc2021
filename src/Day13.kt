enum class FoldDirection { UP, LEFT }

fun main() {

    fun foldUp(dots: List<Pair<Int, Int>>, y: Int): List<Pair<Int, Int>> =
        dots.map {
            if (it.second < y) {
                it
            } else {
                it.first to 2 * y - it.second
            }
        }.distinct()


    fun foldLeft(dots: List<Pair<Int, Int>>, x: Int): List<Pair<Int, Int>> =
        dots.map {
            if (it.first < x) {
                it
            } else {
                2 * x - it.first to it.second
            }
        }.distinct()

    fun parseDots(input: List<String>): List<Pair<Int, Int>> =
        input.asSequence()
            .takeWhile { it.isNotBlank() }
            .map { it.split(",") }
            .map { it[0].toInt() to it[1].toInt() }
            .toList()

    fun parseFoldInstructions(input: List<String>): List<Pair<FoldDirection, Int>> =
        input.asReversed().asSequence()
            .takeWhile { it.isNotBlank() }
            .map { it.substring(11) }
            .map { it.split("=") }
            .map {
                val direction = if (it[0] == "y") FoldDirection.UP else FoldDirection.LEFT
                direction to it[1].toInt()
            }
            .toList()
            .reversed()

    fun printDots(dots: List<Pair<Int, Int>>) {
        val maxX = dots.maxOf { it.first + 1 }
        val maxY = dots.maxOf { it.second + 1 }

        val minMap = Array(maxY) {
            CharArray(maxX) { ' ' }
        }

        dots.forEach {
            minMap[it.second][it.first] = '|'
        }

        minMap.forEach {
            println(it)
        }
    }

    fun part1(input: List<String>): Int {
        val dots: List<Pair<Int, Int>> = parseDots(input)
        val foldInstruction: List<Pair<FoldDirection, Int>> = parseFoldInstructions(input)

        val firstInstruction = foldInstruction.first()
        val folded = if (firstInstruction.first == FoldDirection.UP) {
            foldUp(dots, foldInstruction.first().second)
        } else {
            foldLeft(dots, foldInstruction.first().second)
        }
        return folded.size
    }

    fun part2(input: List<String>): Int {
        var dots: List<Pair<Int, Int>> = parseDots(input)
        val foldInstruction: List<Pair<FoldDirection, Int>> = parseFoldInstructions(input)

        foldInstruction.forEach {
            dots = if (it.first == FoldDirection.UP) {
                foldUp(dots, it.second)
            } else {
                foldLeft(dots, it.second)
            }
        }

        printDots(dots)
        return dots.size
    }

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
