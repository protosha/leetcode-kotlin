package com.github.protosha.minimumsizesubarraysum

import kotlin.math.max
import kotlin.math.min

object MinimumSizeSubarraySum {
    @JvmStatic
    fun main(args: Array<String>) {
        val target = 7
        val nums = intArrayOf(2,3,1,2,4,3)
        println("Input: target = $target, nums = ${nums.joinToString(prefix = "[", postfix = "]")}")
        val output = Solution().minSubArrayLen(target, nums)
        println("Output: $output")
    }
}

class Solution {
    fun minSubArrayLen(target: Int, nums: IntArray): Int {
        var curTargetSum = 0
        var iStart = 0
        var iEnd = 0
        var bestTargetSubArraySize = Int.MAX_VALUE
        while (iStart < nums.size && iEnd <= nums.size) {
            if (curTargetSum < target && iEnd < nums.size) {
                curTargetSum += nums[iEnd]
                iEnd++
            } else {
                curTargetSum -= nums[iStart]
                iStart++
            }

            // we don't need iEnd - iStart + 1 here
            // cause we first add nums[iEnd] to the sum, then increment iEnd
            // so we have that +1 already in iEnd
            if (curTargetSum >= target && bestTargetSubArraySize > iEnd - iStart) {
                bestTargetSubArraySize = iEnd - iStart
            }
        }
        return if (bestTargetSubArraySize == Int.MAX_VALUE) 0 else bestTargetSubArraySize
    }
}

class SolutionNaive {
    fun minSubArrayLen(target: Int, nums: IntArray): Int {
        var subArrayStart = 0
        var subArrayEnd = Int.MAX_VALUE - 1
        for (i in nums.indices) {
            var iSubArraySum = 0
            var iSubArrayEnd = Int.MAX_VALUE - 1
            val iLast = min(nums.lastIndex, i + (subArrayEnd - subArrayStart + 1))
            for (j in i..iLast) {
                iSubArraySum += nums[j]
                if (iSubArraySum >= target) {
                    iSubArrayEnd = j
                    break
                }
            }
            if (subArrayEnd - subArrayStart + 1 > iSubArrayEnd - i + 1) {
                subArrayStart = i
                subArrayEnd = iSubArrayEnd
            }
        }
        return if (subArrayEnd > nums.lastIndex) 0 else subArrayEnd - subArrayStart + 1
    }
}