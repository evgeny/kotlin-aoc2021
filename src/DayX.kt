fun main() {
    fun isUnique(input: String): Boolean {
        for (c1 in input) {
            for (c2 in input) {
                if (c1 == c2) return false
            }
        }
        return true
    }

    println(isUnique("abcd"))
}
