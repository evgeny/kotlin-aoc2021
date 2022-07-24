sealed class Packet(val version: Int, val typeId: Int) {
    data class LiteralPacket(val v: Int, val number: Int) : Packet(v, 4)
    data class OperatorPacket(val v: Int, val lengthTypeId: Int, val subPackets: List<Packet>) : Packet(v, 0)
}

val conversionMap = mapOf(
    '0' to "0000",
    '1' to "0001",
    '2' to "0010",
    '3' to "0011",
    '4' to "0100",
    '5' to "0101",
    '6' to "0110",
    '7' to "0111",
    '8' to "1000",
    '9' to "1001",
    'A' to "1010",
    'B' to "1011",
    'C' to "1100",
    'D' to "1101",
    'E' to "1110",
    'F' to "1111",
)

fun parseLiteralPacketPayload(seq: String): Pair<Int, Int> {
    var i = 0
    var j = 5
    var number = ""

    while (seq.length >= j) {
        val nextChunk = seq.substring(i, j)
        if (nextChunk[0] == '1') {
            number = number.plus(nextChunk.substring(1))
        } else {
            number = number.plus(nextChunk.substring(1))
            break
        }

        i = j
        j = i + 5
    }

    return number.toInt(2) to j
}

fun readPackages(packetSeq: String, num: Int): Pair<List<Packet>, Int> {
    var head = 0
    val list = mutableListOf<Packet>()
    repeat(num) {
        val version = packetSeq.substring(head, head + 3).toInt(2)
        head += 3
        val typeId = packetSeq.substring(head, head + 3).toInt(2)
        head += 3
        if (typeId == 4) {
            val body = packetSeq.substring(head)
            val x = parseLiteralPacketPayload(body)
            val packet = Packet.LiteralPacket(version, x.first)
            list.add(packet)
            head += x.second
        } else {
            val lengthTypeId = packetSeq[head].digitToInt()
            head++
            if (lengthTypeId == 0) {
                val subPacketsLength = packetSeq.substring(head, head + 15).toInt(2)
                head += 15
                val subPacketsSeq = packetSeq.substring(head, head + subPacketsLength)
                head += subPacketsLength
                val packet = Packet.OperatorPacket(version, lengthTypeId, parsePacketSequence(subPacketsSeq))
                list.add(packet)
            } else {
                val subPacketsNumber = packetSeq.substring(head, head + 11).toInt(2)
                head += 11
                val r = readPackages(packetSeq.substring(head), subPacketsNumber)
                val packet = Packet.OperatorPacket(version, lengthTypeId, r.first)
                list.add(packet)
                head += r.second
            }
        }
    }



    return list to head
}

fun parsePacketSequence(packetSeq: String): List<Packet> = buildList {
    var head = 0
    while (head < packetSeq.length - 4) {
        val version = packetSeq.substring(head, head + 3).toInt(2)
        if (version == 0) {
            break
        }
        head += 3
        val typeId = packetSeq.substring(head, head + 3).toInt(2)
        head += 3
        if (typeId == 4) {
            val body = packetSeq.substring(head)
            val x = parseLiteralPacketPayload(body)
            val packet = Packet.LiteralPacket(version, x.first)
            add(packet)
            head += x.second
        } else {
            val lengthTypeId = packetSeq[head].digitToInt()
            head++
            if (lengthTypeId == 0) {
                val subPacketsLength = packetSeq.substring(head, head + 15).toInt(2)
                head += 15
                val subPacketsSeq = packetSeq.substring(head, head + subPacketsLength)
                head += subPacketsLength
                val packet = Packet.OperatorPacket(version, lengthTypeId, parsePacketSequence(subPacketsSeq))
                add(packet)
            } else {
                val subPacketsNumber = packetSeq.substring(head, head + 11).toInt(2)
                head += 11
                val r = readPackages(packetSeq.substring(head), subPacketsNumber)
                val packet = Packet.OperatorPacket(version, lengthTypeId, r.first)
                add(packet)
                head += r.second
            }
        }
    }
}

fun sumVersionNumbers(p: List<Packet>): Int {
    return p.sumOf {
        it.version + if (it is Packet.OperatorPacket) {
            sumVersionNumbers(it.subPackets)
        } else 0
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val hexTest = "620080001611562C8802118E34"
        val hex = input[0]

        val bin = hexTest.fold("") { acc, c -> acc + conversionMap[c] }
        println(bin)

        val p = parsePacketSequence(bin)
        println(p)

        println("sum of versions = ${sumVersionNumbers(p)}")


        return -1
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

    val input = readInput("Day16")
    println(part1(input))
//    println(part2(input))
}
