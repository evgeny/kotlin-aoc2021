fun main() {
    fun part1(input: List<String>): Int {
        val x = input.asSequence()
            .map { it.toInt() }
            .fold(Pair(input[0].toInt(), 0)) { prev, next ->
                if (next > prev.first) {
                    Pair(next, prev.second + 1)
                } else {
                    Pair(next, prev.second)
                }
            }

        return x.second
    }

    fun part2(input: List<String>): Int {
        val firstWindowSum = input.subList(0, 3).sumOf { it.toInt() }
        val x = input.asSequence()
            .map { it.toInt() }
            .windowed(3)
            .fold(Pair(firstWindowSum, 0)) { prev, next ->
                val nextWindowSum = next.sum()
                if (nextWindowSum > prev.first) {
                    Pair(nextWindowSum, prev.second + 1)
                } else {
                    Pair(nextWindowSum, prev.second)
                }
            }

        return x.second
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
