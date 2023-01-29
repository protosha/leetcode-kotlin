package com.github.protosha.textjustification

import java.security.InvalidParameterException
import kotlin.math.max

class Solution {
    fun fullJustify(words: Array<String>, maxWidth: Int): List<String> {
        if (words.isEmpty()) {
            return emptyList()
        }
        var from = 0
        val lines = mutableListOf<Line>()
        outer@ while (from < words.size - 1) {
            var wordsLength = 0
            inner@ for (i in from until words.size) {
                if (wordsLength + words[i].length + (i - from) > maxWidth) {
                    lines.add(Line(Words(from until i, words), maxWidth))
                    from = i
                    break@inner
                } else if (i == words.size - 1) {
                    break@outer
                }
                wordsLength += words[i].length
            }
        }
        lines.add(Line(Words(from until words.size, words), maxWidth))
        return lines.mapIndexed { i, line ->
            if (i == lines.lastIndex)
                line.toLeftJustifiedString()
            else
                line.toFullyJustifiedString()
        }
    }
}

class Line(
    val words: Words,
    private val maxWidth: Int
) {
    private val totalNonSpaces = words.map { it.length }.sum()
    private val totalSpaces = maxWidth - totalNonSpaces

    fun toFullyJustifiedString() = if (words.size == 0) {
        ""
    } else if (words.size == 1) {
        words[0] + " ".repeat(max(0, maxWidth - words[0].length))
    } else if (totalSpaces >= words.size - 1) {
        val minSpacing = totalSpaces / (words.size - 1)
        val extraSpaceUntil = totalSpaces - minSpacing * (words.size - 1)
        var i = 0
        var line = ""
        for (word in words) {
            if (i < extraSpaceUntil) {
                line += word + " ".repeat(minSpacing + 1)
            } else if (i < words.size - 1) {
                line += word + " ".repeat(minSpacing)
            } else {
                line += word
            }
            i++
        }
        line
    } else {
        throw InvalidParameterException("Unable to make a valid line of required maxWidth")
    }

    fun toLeftJustifiedString() = if (words.size == 0) {
        ""
    } else {
        words.joinToString(" ") + " ".repeat(max(0, totalSpaces - words.size + 1))
    }
}

class Words(
    private val fromTo: IntRange,
    private val allWords: Array<String>
): Iterable<String> {
    val size get() = fromTo.last - fromTo.first + 1

    override fun iterator() = object : Iterator<String> {
        var i = fromTo.first
        override fun hasNext() = i <= fromTo.last
        override fun next() = allWords[i++]
    }

    override fun toString(): String {
        return joinToString(prefix = "[", postfix = "]") { "\"$it\"" }
    }

    operator fun get(i: Int) = allWords[fromTo.first + i]
}