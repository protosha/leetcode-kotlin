package com.github.protosha.medianoftwosortedarrays

import java.time.Duration
import java.time.Instant
import kotlin.random.Random

fun main(args: Array<String>) {
    val data = mutableListOf<Pair<IntArray, IntArray>>()
    for (i in 1..10) {
        val size1 = Random.nextInt(0, 10000000)
        val nums1 = List(size1) { Random.nextInt(0, 100) }
            .sorted().toIntArray()
        val size2 = Random.nextInt(0, 10000000)
        val nums2 = List(size2) { Random.nextInt(0, 100) }
            .sorted().toIntArray()
        data.add(Pair(nums1, nums2))
    }

    Instant.now().let {
        for (case in data) {
            Solution().findMedianSortedArrays(case.first, case.second)
        }
        println("BINARY SEARCH SOLUTION (${data.size} iterations): ${Duration.between(it, Instant.now()).toMillis()} ms")
    }

    Instant.now().let {
        for (case in data) {
            Solution1().findMedianSortedArrays(case.first, case.second)
        }
        println("CYCLE SOLUTION (${data.size} iterations): ${Duration.between(it, Instant.now()).toMillis()} ms")
    }

//    val solutions = mutableListOf<Quadruple<IntArray, IntArray, Double, Double>>()
//    for (case in data) {
//        solutions.add(
//            Quadruple(
//                case.first,
//                case.second,
//                Solution1().findMedianSortedArrays(case.first, case.second),
//                Solution().findMedianSortedArrays(case.first, case.second),
//            )
//        )
//    }
//
//    for (s in solutions) {
//        if (s.third != s.fourth) {
//            println(
//                """
//                    WRONG SOLUTION:
//                    {
//                        "nums1": ${s.first},
//                        "nums2": ${s.second},
//                        "expected": ${s.third},
//                        "actual": ${s.fourth}
//                    }
//                """.trimIndent()
//            )
//        }
//    }
}

class Solution {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        return findMedian(IntArraySlice(nums1), IntArraySlice(nums2))
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
                if (nums1[nums1.size - 1] > nums2[nums2.size - 1]) nums1.slice(0, nums1.size - 1) else nums2.slice(0, nums2.size - 1),
                if (nums1[nums1.size - 1] > nums2[nums2.size - 1]) nums2 else nums1,
            )
            val m2 = findMedian(
                if (nums1[0] < nums2[0]) nums1.slice(1, nums1.size - 1) else nums2.slice(1, nums2.size - 1),
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

    private class IntArraySlice(private val arr: IntArray, private val start: Int = 0, val size: Int = arr.size) {
        operator fun get(i: Int) = arr[start + i]
        fun slice(start: Int, size: Int) = IntArraySlice(arr, start, size)
        fun isEmpty() = size == 0
        fun median() = when {
            isEmpty() -> 0.0
            size % 2 == 0 -> (this[size / 2] + this[size / 2 - 1]) / 2.0
            else -> this[size / 2].toDouble()
        }
    }
}



class Solution1 {
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

fun Int.isOdd() = this % 2 != 0
fun Int.isEven() = this % 2 == 0

data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
)