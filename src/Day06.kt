fun main() {
    fun part1(input: List<String>): Int {
        var fishes: List<Int> = input[0].split(",").map { it.toInt() }.toList()

        for (day in 0 until 80) {
            var newFishesWereBorn = 0
            fishes = fishes.map {
                if (it == 0) {
                    newFishesWereBorn++
                    6
                } else {
                    it - 1
                }
            }

            fishes = fishes +  IntArray(newFishesWereBorn) { 8 }.toList()
        }


        return fishes.toList().size
    }

    fun part2(input: List<String>): Int {
        return -1
    }

    val input = readInput("Day06_test")
    println(part1(input))
    println(part2(input))
}
