fun main() {

    val forwardRegex = """forward (\d)""".toRegex()
    val downRegex = """down (\d)""".toRegex()
    val upRegex = """up (\d)""".toRegex()

    fun SubmarinePosition.applyMove(move: String): SubmarinePosition {
        forwardRegex.find(move)?.let {
            return this.copy(x = x + it.groups[1]!!.value.toInt())
        }

        downRegex.find(move)?.let {
            return this.copy(deep = deep + it.groups[1]!!.value.toInt())
        }

        upRegex.find(move)?.let {
            return this.copy(deep = deep - it.groups[1]!!.value.toInt())
        }

        return this
    }

    fun SubmarinePosition.applyMoveWithAim(move: String): SubmarinePosition {
        forwardRegex.find(move)?.let {
            val value = it.groups[1]!!.value.toInt()
            return this.copy(x = x + value, deep = deep + (aim * value))
        }

        downRegex.find(move)?.let {
            return this.copy(aim = aim + it.groups[1]!!.value.toInt())
        }

        upRegex.find(move)?.let {
            return this.copy(aim = aim - it.groups[1]!!.value.toInt())
        }

        return this
    }

    fun part1(input: List<String>): Int {
        val position = input
            .fold(SubmarinePosition()) { currentPosition, move ->
                currentPosition.applyMove(move)
            }

        return position.deep * position.x
    }

    fun part2(input: List<String>): Int {
        val position = input
            .fold(SubmarinePosition()) { currentPosition, move ->
                currentPosition.applyMoveWithAim(move)
            }

        return position.deep * position.x
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

data class SubmarinePosition(val x: Int = 0, val deep: Int = 0, val aim: Int = 0)


