fun main() {
    fun part1(input: List<String>): Int {

        val heightMap = input.map { line -> line.toCharArray().map { it.digitToInt() } }

        fun isMin(i: Int, j: Int): Boolean {
            val center = heightMap[i][j]
            return runCatching { center < heightMap[i - 1][j] }.getOrDefault(true)
                    && runCatching { center < heightMap[i + 1][j] }.getOrDefault(true)
                    && runCatching { center < heightMap[i][j - 1] }.getOrDefault(true)
                    && runCatching { center < heightMap[i][j + 1] }.getOrDefault(true)
        }

        var riskLevel = 0
        heightMap.forEachIndexed { i, line ->
            line.forEachIndexed { j, h ->
                if (isMin(i, j)) {
                    riskLevel += h + 1
                }
            }
        }

        return riskLevel

    }

    fun part2(input: List<String>): Int {
        val heightMap = input.map { line -> line.toCharArray().map { it.digitToInt() } }
        val visitedNodes = mutableSetOf<Pair<Int, Int>>()
        val basinsSizes = mutableListOf<Int>()

        fun scan(i: Int, j: Int, basin: MutableSet<Pair<Int, Int>>) {
            val node = i to j
            if (basin.contains(node)) {
                return
            }

            val nodeResult = runCatching {
                heightMap[i][j]
            }.getOrNull()

            if (nodeResult == null || nodeResult == 9) {
                visitedNodes.add(node)
                return
            }

            basin.add(i to j)
            scan(i + 1, j, basin)
            scan(i, j + 1, basin)
            scan(i - 1, j, basin)
            scan(i, j - 1, basin)
        }

        heightMap.forEachIndexed { i, line ->
            line.forEachIndexed { j, h ->
                if (!visitedNodes.contains(i to j)) {
                    val basin = mutableSetOf<Pair<Int, Int>>()
                    scan(i, j, basin)
                    basinsSizes.add(basin.size)
                    visitedNodes.addAll(basin)
                }
            }
        }

        return basinsSizes
            .sortedDescending().take(3).fold(1) { acc: Int, size: Int -> acc * size }
    }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
