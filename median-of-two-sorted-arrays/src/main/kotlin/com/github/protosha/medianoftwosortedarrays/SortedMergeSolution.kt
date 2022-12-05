package com.github.protosha.medianoftwosortedarrays

class SortedMergeSolution {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val totalSize = nums1.size + nums2.size
        if (totalSize == 0) {
            return 0.0
        }
        val isTotalSizeEven = totalSize % 2 == 0
        val halfTotalSize = totalSize / 2
        var i1 = 0
        var i2 = 0

        val median = ArrayList<Int>(2)
        while (i1 < nums1.size || i2 < nums2.size) {
            if (i1 >= nums1.size) {
                addMedianIfNeeded(median, nums2, i2, i1 + i2, halfTotalSize, isTotalSizeEven)
                i2++
            } else if (i2 >= nums2.size) {
                addMedianIfNeeded(median, nums1, i1, i1 + i2, halfTotalSize, isTotalSizeEven)
                i1++
            } else {
                if (nums1[i1] < nums2[i2]) {
                    addMedianIfNeeded(median, nums1, i1, i1 + i2, halfTotalSize, isTotalSizeEven)
                    i1++
                } else {
                    addMedianIfNeeded(median, nums2, i2, i1 + i2, halfTotalSize, isTotalSizeEven)
                    i2++
                }
            }

            if (isTotalSizeEven && median.size == 2) {
                break
            } else if (!isTotalSizeEven && median.size == 1) {
                break
            }
        }
        return median.average()
    }

    private fun addMedianIfNeeded(
        median: MutableList<Int>,
        fromNums: IntArray,
        i: Int,
        iTotal: Int,
        halfTotalSize: Int,
        isTotalSizeEven: Boolean
    ) {
        if (isTotalSizeEven) {
            if (iTotal == halfTotalSize - 1 || iTotal == halfTotalSize) {
                median.add(fromNums[i])
            }
        } else {
            if (iTotal == halfTotalSize) {
                median.add(fromNums[i])
            }
        }
    }
}
