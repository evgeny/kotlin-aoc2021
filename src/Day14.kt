fun main() {
    fun step(sequence: String, insertions: Map<String, String>): String = sequence.asSequence()
        .windowed(2)
        .map { it.joinToString("") }
        .fold("") { acc, chars ->
            val insertion = insertions[chars]
            if (acc.isEmpty()) {
                "${chars[0]}$insertion${chars[1]}"
            } else {
                "$acc$ insertion${chars[1]}"
            }

        }


    fun part1(input: List<String>): Int {
        val initSeq = input[0]
        val insertions = input.subList(2, input.size)
            .map { it.split(" -> ") }.associate { it[0] to it[1] }

        val finalSequence = (0 until 10).fold(initSeq) { acc, _ ->
            step(acc, insertions)
        }

        val groups = finalSequence.groupBy { it }
        return groups.maxOf { it.value.size } - groups.minOf { it.value.size }
    }

    fun part2(input: List<String>): Int {
        val initSeq = input[0]
        val insertions = input.subList(2, input.size)
            .map { it.split(" -> ") }.associate { it[0] to it[1] }

        val finalSequence = (0 until 40).fold(initSeq) { acc, i ->
            println("step running $i")
            step(acc, insertions)
        }

        val groups = finalSequence.groupBy { it }
        return groups.maxOf { it.value.size } - groups.minOf { it.value.size }
    }

    val input = readInput("Day14_test")
    println(part1(input))
    println(part2(input))
//    NCNBCHB
}
