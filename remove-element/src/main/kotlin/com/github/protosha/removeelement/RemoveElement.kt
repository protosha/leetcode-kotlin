package com.github.protosha.removeelement

object RemoveElement {
    @JvmStatic
    fun main(args: Array<String>) {
        val nums = intArrayOf(0,1,2,2,3,0,4,2)
        val `val` = 2
        val k = Solution().removeElement(nums, `val`)
        println(nums)
        println(k)
    }
}

class Solution {
    fun removeElement(nums: IntArray, `val`: Int): Int {
        var ilast = nums.lastIndex
        for (i in nums.lastIndex downTo 0) {
            if (nums[i] == `val`) {
                val temp = nums[i]
                nums[i] = nums[ilast]
                nums[ilast] = temp
                ilast--
            }
        }
        return ilast + 1
    }
}