import kotlin.math.abs
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val values = input[0].split(",").map { it.toInt() }
        val mean = values.average()
        val median = values.sorted().let {
            if (it.size.mod(2) == 0) {
                (it[it.size / 2] + it[it.size / 2 - 1]) / 2
            } else {
                it[it.size / 2]
            }
        }

        var min = Int.MAX_VALUE
        for (i in median..mean.toInt()) {
            val sum = values.sumOf { abs(it - i) }
            min = min(min, sum)
        }

        return min
    }

    fun part2(input: List<String>): Int {
        return -1
    }

//    val testInput = listOf("16,1,2,0,4,2,7,1,2,14")
    val input = readInput("Day07")
    println(part1(input))
//    println(part2(input))
}
