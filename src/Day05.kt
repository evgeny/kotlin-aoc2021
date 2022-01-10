import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {

    class Venture(textLine: String) {
        val firstPoint: Pair<Int, Int>
        val secondPoint: Pair<Int, Int>

        init {
            val points = textLine.split(" -> ")
            firstPoint = points[0].split(",").let {
                it[0].toInt() to it[1].toInt()
            }

            secondPoint = points[1].split(",").let {
                it[0].toInt() to it[1].toInt()
            }
        }

        val isHorizontal = firstPoint.second == secondPoint.second
        val isVertical = firstPoint.first == secondPoint.first

        val maxX = max(firstPoint.first, secondPoint.first)
        val minX = min(firstPoint.first, secondPoint.first)
        val maxY = max(firstPoint.second, secondPoint.second)
        val minY = min(firstPoint.second, secondPoint.second)

        fun containsPoint(x: Int, y: Int): Boolean {
            return when {
                isHorizontal -> y == maxY && (x in minX..maxX)
                isVertical -> x == maxX && (y in minY..maxY)
                else -> false
            }
        }

        fun containsPointDiagonalIncl(x: Int, y: Int): Boolean {
            return when {
                isHorizontal -> y == maxY && (x in minX..maxX)
                isVertical -> x == maxX && (y in minY..maxY)
                else -> {
                    x in minX..maxX && y in minY .. maxY && (abs(x - firstPoint.first) == abs(y - firstPoint.second))
                }
            }
        }
    }


    fun part1(input: List<String>): Int {
        val ventures = input.map { Venture(it) }

        val size = ventures.fold(0 to 0) { acc, venture ->
            max(acc.first, venture.maxX) to max(acc.second, venture.maxY)
        }

        var dangerousPointTotal = 0
        for (x in 0 .. size.first) {
            for (y in 0 .. size.second) {
                var count = 0
                for (v in ventures) {
                    if (v.containsPoint(x, y)) {
                        count++
                    }

                    if (count >= 2) {
                        dangerousPointTotal++
                        break
                    }
                }
            }
        }

        return dangerousPointTotal
    }

    fun part2(input: List<String>): Int {
        val ventures = input.map { Venture(it) }

        val size = ventures.fold(0 to 0) { acc, venture ->
            max(acc.first, venture.maxX) to max(acc.second, venture.maxY)
        }

        var dangerousPointTotal = 0
        for (x in 0 .. size.first) {
            for (y in 0 .. size.second) {
                var count = 0
                for (v in ventures) {
                    if (v.containsPointDiagonalIncl(x, y)) {
                        count++
                    }

                    if (count >= 2) {
                        dangerousPointTotal++
                        break
                    }
                }
            }
        }

        return dangerousPointTotal
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}