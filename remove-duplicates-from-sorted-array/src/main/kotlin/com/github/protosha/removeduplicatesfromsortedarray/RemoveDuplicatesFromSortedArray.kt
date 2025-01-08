package com.github.protosha.removeduplicatesfromsortedarray

object RemoveDuplicatesFromSortedArray {
    @JvmStatic
    fun main(args: Array<String>) {
        val nums = intArrayOf(0,0,1,1,1,2,2,3,3,4)
        val k = Solution().removeDuplicates(nums)
        println(nums.joinToString())
        println(k)
    }
}

class Solution {
    fun removeDuplicates(nums: IntArray): Int {
        var lastUniqueValue = nums[0]
        var lastUniqueIndex = 0
        for (i in 0..nums.lastIndex) {
            if (lastUniqueValue != nums[i]) {
                lastUniqueValue = nums[i]
                lastUniqueIndex++
                nums[lastUniqueIndex] = lastUniqueValue
            }
        }
        return lastUniqueIndex + 1
    }
}