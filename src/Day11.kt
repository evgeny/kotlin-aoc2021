fun main() {
    fun printM(matrix: List<MutableList<Int>>) {
        matrix.forEach {
            println(it.toString())
        }

        println("--------------")
    }

    fun flash(i: Int, j: Int, matrix: List<MutableList<Int>>) {
        matrix[i][j] = 0

        runCatching {
            if (matrix[i + 1][j + 1] > 0)
                matrix[i + 1][j + 1] = matrix[i + 1][j + 1] + 1
        }
        runCatching {
            if (matrix[i][j + 1] > 0)
                matrix[i][j + 1] = matrix[i][j + 1] + 1
        }
        runCatching {
            if (matrix[i + 1][j] > 0)
                matrix[i + 1][j] = matrix[i + 1][j] + 1
        }
        runCatching {
            if (matrix[i - 1][j] > 0)
                matrix[i - 1][j] = matrix[i - 1][j] + 1
        }
        runCatching {
            if (matrix[i][j - 1] > 0)
                matrix[i][j - 1] = matrix[i][j - 1] + 1
        }
        runCatching {
            if (matrix[i - 1][j - 1] > 0)
                matrix[i - 1][j - 1] = matrix[i - 1][j - 1] + 1
        }
        runCatching {
            if (matrix[i + 1][j - 1] > 0)
                matrix[i + 1][j - 1] = matrix[i + 1][j - 1] + 1
        }
        runCatching {
            if (matrix[i - 1][j + 1] > 0)
                matrix[i - 1][j + 1] = matrix[i - 1][j + 1] + 1
        }
    }

    fun step(matrix: List<MutableList<Int>>): Int {
        var flashesInStep = 0

        matrix.forEachIndexed { i, row ->
            row.forEachIndexed { j, octopus ->
                matrix[i][j] = octopus + 1
            }
        }

        var flash = true
        while (flash) {
            flash = false
            matrix.forEachIndexed { i, row ->
                row.forEachIndexed { j, octopus ->
                    if (octopus > 9) {
                        flash = true
                        flashesInStep++
                        flash(i, j, matrix)
                    }
                }
            }
        }

//        printM(matrix)
        return flashesInStep
    }

    fun isSynced(matrix: List<MutableList<Int>>): Boolean {
        matrix.forEachIndexed { i, row ->
            row.forEachIndexed { j, octopus ->
                if(matrix[i][j] != 0) return false
            }
        }

        return true
    }



    fun part1(input: List<String>): Int {
        val matrix = input.map { it.map { it.digitToInt() }.toMutableList() }

        val total = (1..100).fold(0) { acc, i ->
            acc + step(matrix)
        }

        return total
    }

    fun part2(input: List<String>): Int {
        val matrix = input.map { it.map { it.digitToInt() }.toMutableList() }

        var isUnsynced = true
        var steps = 0
        while (isUnsynced) {
            steps++
            step(matrix)
            isUnsynced = !isSynced(matrix)
        }
        return steps
    }

    val input = readInput("Day11")
//    println(part1(input))
    println(part2(input))
}
