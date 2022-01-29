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


    fun part1(input: List<String>): Int {
        val result = input.sumOf {
            isCorrupted(it)?.let { broken ->
                bracketScore[broken]
            } ?: 0
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val firstWindowSum = input.subList(0, 3).sumOf { it.toInt() }
        val x = input.asSequence()
            .map { it.toInt() }
            .windowed(3)
            .fold(Pair(firstWindowSum, 0)) { prev, next ->
                val nextWindowSum = next.sum()
                if (nextWindowSum > prev.first) {
                    Pair(nextWindowSum, prev.second + 1)
                } else {
                    Pair(nextWindowSum, prev.second)
                }
            }

        return x.second
    }

    val input = readInput("Day10")
    println(part1(input))
//    println(part2(input))
}
