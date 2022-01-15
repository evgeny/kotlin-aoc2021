fun main() {

    val cache = Array(257) { mutableMapOf<Int, Long>() }

    fun part1(input: List<String>): Int {
        var fishes: List<Int> = input[0].split(",").map { it.toInt() }

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

            fishes = fishes + IntArray(newFishesWereBorn) { 8 }.toList()
        }


        return fishes.toList().size
    }

    fun fishOffspring(days: Int, startDay: Int): Long {
        val cached = cache[days][startDay]

        if (cached != null) return cached
        if (days <= startDay) return 1

        val remainingDays = days - startDay - 1

        var fishes = 1L
        for (d in remainingDays downTo 0 step 7) {
            fishes += fishOffspring(d, 8)
        }

        cache[days][startDay] = fishes
        return fishes
    }

    fun part2(input: List<String>): Long {
        val fishes: List<Int> = input[0].split(",").map { it.toInt() }
        return fishes.sumOf { fishOffspring(256, it) }
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
