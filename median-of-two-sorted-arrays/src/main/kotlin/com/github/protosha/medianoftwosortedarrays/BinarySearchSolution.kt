package com.github.protosha.medianoftwosortedarrays

import com.github.protosha.medianoftwosortedarrays.extension.median
import com.github.protosha.utils.collection.IntArraySlice
import com.github.protosha.utils.extension.isOdd
import com.github.protosha.utils.extension.sliceRef

class BinarySearchSolution {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        return findMedian(nums1.sliceRef(), nums2.sliceRef())
    }

    private fun findMedian(nums1: IntArraySlice, nums2: IntArraySlice): Double {
        val totalSize = nums1.size + nums2.size
        if (nums1.size + nums2.size == 0) {
            return 0.0
        } else if (nums1.isEmpty()) {
            return nums2.median()
        } else if (nums2.isEmpty()) {
            return nums1.median()
        }

        if (totalSize.isOdd()) {
            val iMedianCandidate1 = searchBinary(nums1) { iSearch, search ->
                val iClosest = searchBinary(nums2) { iCompare, compare ->
                    val diff = search[iSearch] - compare[iCompare]
                    if (diff == 0) {
                        distToMedian(iSearch, search, iCompare, compare)
                    } else {
                        diff
                    }
                }
                return@searchBinary distToMedian(iSearch, search, iClosest, nums2)
            }
            val iClosestCandidate1 = searchBinary(nums2) { iCompare, compare ->
                val diff = nums1[iMedianCandidate1] - compare[iCompare]
                if (diff == 0) {
                    distToMedian(iMedianCandidate1, nums1, iCompare, compare)
                } else {
                    diff
                }
            }

            val iMedianCandidate2 = searchBinary(nums2) { iSearch, search ->
                val iClosest = searchBinary(nums1) { iCompare, compare ->
                    val diff = search[iSearch] - compare[iCompare]
                    if (diff == 0) {
                        distToMedian(iSearch, search, iCompare, compare)
                    } else {
                        diff
                    }
                }
                return@searchBinary distToMedian(iSearch, search, iClosest, nums1)
            }
            return if (distToMedian(iMedianCandidate1, nums1, iClosestCandidate1, nums2) == 0) {
                nums1[iMedianCandidate1].toDouble()
            } else {
                nums2[iMedianCandidate2].toDouble()
            }
        } else {
            val m1 = findMedian(
                if (nums1[nums1.size - 1] > nums2[nums2.size - 1]) nums1.sliceRef(0, nums1.size - 1) else nums2.sliceRef(0, nums2.size - 1),
                if (nums1[nums1.size - 1] > nums2[nums2.size - 1]) nums2 else nums1,
            )
            val m2 = findMedian(
                if (nums1[0] < nums2[0]) nums1.sliceRef(1, nums1.size - 1) else nums2.sliceRef(1, nums2.size - 1),
                if (nums1[0] < nums2[0]) nums2 else nums1,
            )
            return (m1 + m2) / 2.0
        }
    }

    private fun searchBinary(inNums: IntArraySlice, comparator: (Int, IntArraySlice) -> Int): Int
    {
        var iStart = 0
        var iEnd = inNums.size
        var i = (iEnd + iStart) / 2
        var iPrev = 0
        while (i != iPrev) {
            val compareResult = comparator(i, inNums)
            if (compareResult > 0) {
                iStart = i
            } else if (compareResult < 0) {
                iEnd = i
            } else {
                break
            }
            iPrev = i
            i = (iEnd + iStart) / 2
        }
        return i
    }

    private fun distToMedian(i: Int, nums: IntArraySlice, iOther: Int, otherNums: IntArraySlice): Int {
        val halfTotalSize = (nums.size + otherNums.size) / 2
        return when {
            nums[i] <= otherNums[iOther] -> halfTotalSize - (i + iOther)
            else -> halfTotalSize - (i + iOther + 1)
        }
    }
}
