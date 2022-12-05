package com.github.protosha.medianoftwosortedarrays

import java.time.Duration
import java.time.Instant
import kotlin.random.Random

object MedianOfTwoSortedArrays {
    @JvmStatic
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
                BinarySearchSolution().findMedianSortedArrays(case.first, case.second)
            }
            println(
                "BINARY SEARCH SOLUTION (${data.size} iterations): ${
                    Duration.between(it, Instant.now()).toMillis()
                } ms"
            )
        }

        Instant.now().let {
            for (case in data) {
                SortedMergeSolution().findMedianSortedArrays(case.first, case.second)
            }
            println("CYCLE SOLUTION (${data.size} iterations): ${Duration.between(it, Instant.now()).toMillis()} ms")
        }

//    val solutions = mutableListOf<Quadruple<IntArray, IntArray, Double, Double>>()
//    for (case in data) {
//        solutions.add(
//            Quadruple(
//                case.first,
//                case.second,
//                SortedMergeSolution().findMedianSortedArrays(case.first, case.second),
//                BinarySearchSolution().findMedianSortedArrays(case.first, case.second),
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
}
