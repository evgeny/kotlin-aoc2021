fun main() {
    fun part1(input: List<String>): Int {
        val lines = input.map {
            val line = it.split("|")
            val digits = line[0].trim().split(" ")
            val output = line[1].trim().split(" ")

            digits to output
        }

        val outputOnly = lines.map { it.second }.flatten().fold(0) { acc, s ->
            if (s.length == 2 || s.length == 3 || s.length == 7 || s.length == 4) {
                acc + 1
            } else {
                acc
            }
        }
        return outputOnly
    }

    fun part2(input: List<String>): Int {
        return -1
    }


    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
