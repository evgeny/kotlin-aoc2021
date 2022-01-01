import kotlin.math.pow

fun main() {
    fun List<Int>.fromBinArray(): Float = reversed().foldIndexed(0f) {
            index, acc, value -> acc + value * 2f.pow(index)
    }

    fun part1(input: List<String>): Int {
        val resultArray = IntArray(input[0].length)
        val resultArrayFilled = input.fold(resultArray) { result, nextLine ->
            nextLine.forEachIndexed { index, c ->
                val newValue = if (c == '1') {
                    result[index] + 1
                } else {
                    result[index] - 1
                }

                result[index] = newValue
            }

            result
        }

        val gammaRate = resultArrayFilled.map {
            if (it > 0) {
                1
            } else {
                0
            }
        }

        val epsilonRate = resultArrayFilled.map {
            if (it < 0) {
                1
            } else {
                0
            }
        }


        return (gammaRate.fromBinArray() * epsilonRate.fromBinArray()).toInt()
    }

    fun part2(input: List<String>): Int {
        var oxygenRating = input
        var co2Rating = input

        for (i in 0 until input[0].length) {
            val subListsOxygen = oxygenRating.partition { it[i] == '1' }

            oxygenRating = if (subListsOxygen.first.size >= subListsOxygen.second.size) {
                subListsOxygen.first
            } else {
                subListsOxygen.second
            }

            if (oxygenRating.size == 1) break
        }

        for (i in 0 until input[0].length) {
            val subListCo2 = co2Rating.partition { it[i] == '0' }

            co2Rating = if (subListCo2.first.size <= subListCo2.second.size) {
                subListCo2.first
            } else {
                subListCo2.second
            }

            if (co2Rating.size == 1) break
        }

        return (oxygenRating[0].map { it.digitToInt() }.fromBinArray() *
                co2Rating[0].map { it.digitToInt() }.fromBinArray()).toInt()
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}