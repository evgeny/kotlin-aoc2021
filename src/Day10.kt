import java.util.*

fun main() {
    val brackets = mapOf(
        '(' to ')',
        '[' to ']',
        '<' to '>',
        '{' to '}',
    )

    val bracketScore = mapOf(
        ')' to 3,
        ']' to 57,
        '>' to 25137,
        '}' to 1197,
    )

    val bracketPoints = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4,
    )

    fun Char.isOpenBracket() = brackets.contains(this)

    fun Char.match(c: Char): Boolean = brackets[this]?.let { it == c } ?: false


    fun isCorrupted(line: String): Char? {
        val s: Stack<Char> = Stack()
        line.forEach { c ->
            if (c.isOpenBracket()) {
                s.push(c)
            } else {
                if (s.isEmpty()) return null
                val openBracket = s.pop()
                if (!openBracket.match(c)) {
                    return c
                }
            }
        }

        return null
    }

    fun getAutoCompleteIfNotCorrupted(line: String): List<Char> {
        val s: Stack<Char> = Stack()
        val autocompleteList = mutableListOf<Char>()

        line.forEach { c ->
            if (c.isOpenBracket()) {
                s.push(c)
            } else {
                if (s.isEmpty()) return autocompleteList

                val openBracket = s.pop()
                if (!openBracket.match(c)) {
                    return autocompleteList
                }
            }
        }

        return s.mapNotNull { brackets[it] }.reversed()
    }

    fun computeAutocompletePoints(complete: List<Char>): Long = complete.fold(0) { acc: Long, c: Char ->
        acc * 5 + bracketPoints[c]!!
    }

    fun part1(input: List<String>): Int {
        val result = input.sumOf {
            isCorrupted(it)?.let { broken ->
                bracketScore[broken]
            } ?: 0
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val scoreList = input.map {
            getAutoCompleteIfNotCorrupted(it)
        }.filter {
            it.isNotEmpty()
        }.map {
            computeAutocompletePoints(it)
        }

        return scoreList.sorted()[scoreList.size / 2].toInt()
    }

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
