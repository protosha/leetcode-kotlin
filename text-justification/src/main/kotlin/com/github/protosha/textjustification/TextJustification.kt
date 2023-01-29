package com.github.protosha.textjustification

object TextJustification {
    @JvmStatic
    fun main(args: Array<String>) {
        val words = arrayOf("Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do")
        val lines = Solution().fullJustify(words, 20)
        lines.forEach { println("\"$it\"") }
    }
}