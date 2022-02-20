fun main() {
    class Cave(name: String) {
        val isStart = name == "start"
        val isEnd = name == "end"
    }

    fun getAdjacentMatrix(input: List<String>): MutableMap<String, MutableSet<String>> {
        val adjacent: MutableMap<String, MutableSet<String>> = mutableMapOf()

        input.forEach {
            val caves = it.split("-")
            val set1 = adjacent.getOrPut(caves[0]) {
                mutableSetOf()
            }
            set1.add(caves[1])

            if (caves[1] != "end") {
                val set2 = adjacent.getOrPut(caves[1]) {
                    mutableSetOf()
                }
                if (caves[0] != "start") set2.add(caves[0])
            }
        }

        return adjacent
    }

    fun part1(input: List<String>): Int {
        val adjacent: MutableMap<String, MutableSet<String>> = getAdjacentMatrix(input)
        val routes: MutableList<String> = mutableListOf()

        fun traverse(visited: List<String>) {
            val lastCave = visited.last()
            val neighbors = adjacent[lastCave]

            neighbors?.forEach {
                when {
                    (it == "end") -> routes.add((visited + it).joinToString(","))
                    visited.contains(it) -> {
                        if (it.uppercase().equals(it, ignoreCase = false)
                            && !visited.joinToString(",").contains("$lastCave,$it")) {
                            traverse(visited + it)
                        }
                    }
                    else -> traverse(visited + it)
                }
            }
        }

        traverse(listOf("start"))

        return routes.size
    }

    fun part2(input: List<String>): Int {

        return 2
    }

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
