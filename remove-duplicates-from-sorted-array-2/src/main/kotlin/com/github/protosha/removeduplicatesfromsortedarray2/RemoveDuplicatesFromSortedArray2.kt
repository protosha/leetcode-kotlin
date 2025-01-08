package com.github.protosha.removeduplicatesfromsortedarray2

object RemoveDuplicatesFromSortedArray2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val nums = intArrayOf(0,0,1,1,1,1,2,3,3)
        val k = Solution().removeDuplicates(nums)
        println(nums.joinToString())
        println(k)
    }
}

class Solution {
    fun removeDuplicates(nums: IntArray): Int {
        if (nums.size < 2) {
            return 1
        }

        var lastUniqueValue = nums[0]
        var lastUniqueIndex = 0
        var lastUniqueValueCount = 1
        for (i in 1..nums.lastIndex) {
            if (lastUniqueValue == nums[i]) {
                if (lastUniqueValueCount < 2) {
                    lastUniqueValueCount++
                    lastUniqueIndex++
                    nums[lastUniqueIndex] = lastUniqueValue
                }
            } else {
                lastUniqueValue = nums[i]
                lastUniqueIndex++
                nums[lastUniqueIndex] = lastUniqueValue
                lastUniqueValueCount = 1
            }
        }
        return lastUniqueIndex + 1
    }
}