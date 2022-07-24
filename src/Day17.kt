data class Area(val minX: Int, val minY: Int, val maxX: Int, val maxY: Int)

fun main() {
    fun part1(input: List<String>): Int {

        return -1
    }

    fun part2(input: List<String>): Int {
        return -3
    }

    val input = readInput("Day17")
    val a = input[0].substring(15).split(",")
    val xs = a[0].split("..")
    val ys = a[1].trim().substring(2).split("..")

    val area = Area(xs[0].toInt(), ys[0].toInt(), xs[1].toInt(), ys[1].toInt())

    println(part1(input))
    println(part2(input))
}
