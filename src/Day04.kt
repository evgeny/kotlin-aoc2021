fun main() {

    data class BingoCell(val number: Int, var marked: Boolean = false)
    data class LastMatch(val number: Int, val row: Int, val column: Int)

    class BingoBoard(lines: List<String>) {
        private val board: Array<Array<BingoCell>>
        private var lastMatchedDraw: LastMatch? = null
        var score: Int = 0
            private set

        init {
            board = Array(lines.size) { index ->
                lines[index].split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
                    .map { BingoCell(it) }
                    .toTypedArray()

            }
        }

        var hasWinner: Boolean = false
            private set

        fun checkDraw(drawnNumber: Int) {
            for (row in 0 until 5) {
                for (column in 0 until 5) {
                    if (board[row][column].number == drawnNumber) {
                        board[row][column].marked = true
                        lastMatchedDraw = LastMatch(drawnNumber, row, column)
                    }
                }
            }

            val lastDraw = lastMatchedDraw
            if (lastDraw != null) {
                hasWinner = checkTheWinner(lastDraw.row, lastDraw.column)
                if (hasWinner) calculateScore(lastDraw.number)
            }
        }

        private fun checkTheWinner(row: Int, column: Int): Boolean {
            var isRowTheWinner = true
            var isColumnTheWinner = true

            for (k in 0 until 5) {
                isRowTheWinner = isRowTheWinner && board[row][k].marked
                isColumnTheWinner = isColumnTheWinner && board[k][column].marked
            }

            return isColumnTheWinner || isRowTheWinner
        }

        private fun calculateScore(lastDraw: Int) {
            val sumUnmarkedCells = board
                .flatMap { it.asIterable() }
                .fold(0) { acc, bingoCell ->
                if (bingoCell.marked) {
                    acc
                } else {
                    bingoCell.number + acc
                }
            }

            score = sumUnmarkedCells * lastDraw
        }
    }

    fun part1(input: List<String>): Int {
        val draw = input[0].split(",").map { it.toInt() }

        val boards = input.subList(2, input.size)
            .filter { it.isNotBlank() }
            .chunked(5)
            .map { BingoBoard(it) }

        draw.forEach { d ->
            boards.forEach {
                it.checkDraw(d)
                if (it.hasWinner) {
                    return it.score
                }
            }
        }

        return -1
    }

    fun part2(input: List<String>): Int {
        val draw = input[0].split(",").map { it.toInt() }

        val boards = input.subList(2, input.size)
            .filter { it.isNotBlank() }
            .chunked(5)
            .map { BingoBoard(it) }

        var lastBoardScore = 0
        draw.forEach { d ->
            boards.forEach {
                if (!it.hasWinner) {
                    it.checkDraw(d)

                    if (it.hasWinner) {
                        lastBoardScore = it.score
                    }
                }
            }
        }
        return lastBoardScore
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}