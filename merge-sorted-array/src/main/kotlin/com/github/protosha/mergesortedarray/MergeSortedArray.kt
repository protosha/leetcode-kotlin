package com.github.protosha.mergesortedarray

object MergeSortedArray {
    @JvmStatic
    fun main(args: Array<String>) {
        val nums1 = intArrayOf(2,0)
        val m = 1
        val nums2 = intArrayOf(1)
        val n = 1
        Solution().merge(nums1, m, nums2, n)
        println(nums1.joinToString())
    }
}

class Solution {
    fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
        val nums1Backup = nums1.copyOf(m)
        var i = 0
        var i1 = 0
        var i2 = 0
        val mn = m + n
        while (i < mn) {
            if (i1 < m && i2 < n) {
                if (nums1Backup[i1] < nums2[i2]) {
                    nums1[i1 + i2] = nums1Backup[i1]
                    i1++
                } else {
                    nums1[i1 + i2] = nums2[i2]
                    i2++
                }
            } else if (i1 < m) {
                nums1[i1 + i2] = nums1Backup[i1]
                i1++
            } else if (i2 < n) {
                nums1[i1 + i2] = nums2[i2]
                i2++
            }
            i++
        }
    }
}