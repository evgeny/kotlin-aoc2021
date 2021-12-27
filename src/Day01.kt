fun main() {
    val measurements = readInput("Day01")
    val x = measurements.asSequence()
        .map { it.toInt() }
        .fold(Pair(measurements[0].toInt(), 0)) { prev, next ->
            if (next > prev.first) {
                Pair(next, prev.second + 1)
            } else {
                Pair(next, prev.second)
            }
        }

    println(x.second)
/*    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 1)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))*/
}
