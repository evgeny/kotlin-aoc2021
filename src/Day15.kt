import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Int {
        val matrix = input.map { line -> line.toCharArray().map { it.digitToInt() } }
        val maxY = matrix.size - 1
        val maxX = matrix[0].size - 1
        val levels: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()


        println("maxX=$maxX, maxY=$maxY")

        fun riskLevel(y: Int, x: Int): Int {
//            println("x=$x, y=$y, value=${matrix[x][y]}")
            val level = if (levels.containsKey(y to x)) {
                levels[y to x]!!
            } else if (x == maxX && y == maxY) {
                matrix[y][x]
            } else if (x == maxX) {
                matrix[y][x] + riskLevel(y + 1, x)
            } else if (y == maxY) {
                matrix[y][x] + riskLevel(y, x + 1)
            } else {
                matrix[y][x] + min(riskLevel(y + 1, x), riskLevel(y, x + 1))
            }

            levels[y to x] = level
            return level
        }

        val minLevel =  min(riskLevel(1, 0), riskLevel(0, 1))
//        println(levels)
        return minLevel
    }

    val input = readInput("Day15")
    println(part1(input))
//    println(part2(input))
}
