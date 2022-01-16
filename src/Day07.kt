import kotlin.math.abs
import kotlin.math.min
import kotlin.math.roundToInt

fun main() {
    fun part1(input: List<String>): Int {
        val values = input[0].split(",").map { it.toInt() }
        val mean = values.average().roundToInt()
        val median = values.sorted().let {
            if (it.size.mod(2) == 0) {
                (it[it.size / 2] + it[it.size / 2 - 1]) / 2
            } else {
                it[it.size / 2]
            }
        }

        var min = Int.MAX_VALUE
        for (i in median..mean) {
            val sum = values.sumOf { abs(it - i) }
            min = min(min, sum)
        }

        return min
    }

    fun part2(input: List<String>): Int {
        val values = input[0].split(",").map { it.toInt() }.sorted()
        val mean = values.average().roundToInt()
        val median = values.let {
            if (it.size.mod(2) == 0) {
                (it[it.size / 2] + it[it.size / 2 - 1]) / 2
            } else {
                it[it.size / 2]
            }
        }

        var min = Int.MAX_VALUE
        for (i in median..mean) {
            val sum = values.sumOf {
                val distance = abs(it - i)
                (distance * (distance + 1)) / 2
            }
            min = min(min, sum)
        }

        return min
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
