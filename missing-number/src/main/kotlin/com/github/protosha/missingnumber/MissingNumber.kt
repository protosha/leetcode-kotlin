package com.github.protosha.missingnumber

import com.github.protosha.utils.extension.joinToPrettyString

object MissingNumber {
    @JvmStatic
    fun main(args: Array<String>) {
        val intList = List(12) { i -> i }
        for (i in intList) {
            val inputList = (intList - i).toIntArray()
            val output = XorSolution().missingNumber(inputList)
            println("BEGIN")
            println("List original:   ${inputList.joinToPrettyString()}")
            println("Missing number:  $i (${i.toString(2).padStart(8, '0')})")
            println("Output:          $output (${output.toString(2).padStart(8, '0')})")
            println("END")
        }
    }
}

class XorSolution {
    fun missingNumber(nums: IntArray): Int {
        val allNums = List(nums.size + 1) { it }
        return allNums.reduce {acc, num -> acc xor num } xor nums.reduce { acc, num -> acc xor num }
    }
}