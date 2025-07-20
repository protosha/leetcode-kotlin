package com.github.protosha.lettercasepermutation

import kotlin.math.pow

object LetterCasePermutation {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = "hello123world"
        println("Input: $input")
        val output = StackSolution().letterCasePermutation(input)
        val correctOutput = NaiveSolution().letterCasePermutation(input)
        println("Total permutations: ${output.size}")
        println("Correct total permutations: ${correctOutput.size}")
        println("Output: ${output.joinToString(prefix = "[", postfix = "]") { "\"$it\""}}")
        println("Missing: ${correctOutput.sorted() - output.sorted()}")
    }
}

class NaiveSolution {
    fun letterCasePermutation(s: String): List<String> {
        return permutationsFor(s)
    }

    private fun permutationsFor(s: String): List<String> {
        if (s.length == 1) {
            return if (s[0].isDigit()) {
                listOf(s)
            } else {
                listOf(s.uppercase(), s.lowercase())
            }
        }
        val perms = mutableListOf<String>()
        for (subperm1 in permutationsFor(s.take(1))) {
            for (subperm2 in permutationsFor(s.drop(1))) {
                perms.add(subperm1 + subperm2)
            }
        }
        return perms
    }
}

class StackSolution {
    fun letterCasePermutation(s: String): List<String> {
        val totalLetters = s.filter { it.isLetter() }.length
        val totalElse = s.filter { !it.isLetter() }.length
        val totalPerms = ((2.0).pow(totalLetters) + totalElse).toInt()
        val stack = ArrayList<StackOperation>(totalPerms)
        stack.add(StackOperation(s, 0))
        val perms = ArrayList<String>(totalPerms)
        while (stack.isNotEmpty()) {
            val operation = stack.removeLast()
            if (operation.charAt <= operation.value.lastIndex) {
                val value = operation.value
                if (value[operation.charAt].isLetter()) {
                    val left = value.substring(0, operation.charAt)
                    val right = value.substring(operation.charAt + 1)
                    val value1 = left + value[operation.charAt].uppercase() + right
                    val value2 = left + value[operation.charAt].lowercase() + right
                    if (operation.charAt < operation.value.lastIndex) {
                        stack.add(StackOperation(value1, operation.charAt + 1))
                        stack.add(StackOperation(value2, operation.charAt + 1))
                    } else {
                        perms.add(value1)
                        perms.add(value2)
                    }
                } else {
                    if (operation.charAt < operation.value.lastIndex) {
                        stack.add(StackOperation(value, operation.charAt + 1))
                    } else {
                        perms.add(value)
                    }
                }
            }
        }
        return perms
    }

    data class StackOperation(
        val value: String,
        val charAt: Int
    )
}

