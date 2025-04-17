package com.github.protosha.candy

object Candy {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = intArrayOf(0)
        println("Input: ${input.joinToString()}")
        println(Solution().candy(input))
    }
}

class Solution {
    fun candy(ratings: IntArray): Int {
        var totalCandies = 0
        for (i in ratings.indices) {
            totalCandies += decideCandies(i, ratings)
        }
        return totalCandies
    }

    private fun decideCandies(iChild: Int, ratings: IntArray): Int {
        if (ratings.size == 1) {
            return 1
        }
        var rightSlopeSize = 0
        if (iChild != ratings.lastIndex) {
            for (i in iChild + 1 .. ratings.lastIndex) {
                if (ratings[i - 1] <= ratings[i]) {
                    break
                }
                rightSlopeSize++
            }
        }
        var leftSlopeSize = 0
        if (iChild != 0) {
            for (i in iChild - 1 downTo 0) {
                if (ratings[i + 1] <= ratings[i]) {
                    break
                }
                leftSlopeSize++
            }
        }
        return if (leftSlopeSize > rightSlopeSize) leftSlopeSize + 1 else rightSlopeSize + 1
    }
}

class SolutionNaive {
    fun candy(ratings: IntArray): Int {
        var totalCandies = 0
        var lastDistCandies = Int.MAX_VALUE
        val childrenWithCandies = IntArray(ratings.size)
        while (lastDistCandies != 0) {
            lastDistCandies = distributeCandies(childrenWithCandies, ratings)
            totalCandies += lastDistCandies
        }
        println(childrenWithCandies.joinToString())
        return totalCandies
    }

    private fun distributeCandies(childrenWithCandies: IntArray, ratings: IntArray): Int {
        var distCandies = 0
        for (i in ratings.indices) {
            if (childrenWithCandies[i] == 0) {
                childrenWithCandies[i] = 1
                distCandies++
            } else if (ratings.size > 1 && i == 0 && ratings[i] > ratings[i + 1]) {
                if (childrenWithCandies[i] <= childrenWithCandies[i + 1]) {
                    distCandies += childrenWithCandies[i + 1] - childrenWithCandies[i]
                    childrenWithCandies[i] = childrenWithCandies[i + 1] + 1
                    distCandies++
                }
            } else if (ratings.size > 1 && i == ratings.lastIndex && ratings[i] > ratings[i - 1]) {
                if (childrenWithCandies[i] <= childrenWithCandies[i - 1]) {
                    distCandies += childrenWithCandies[i - 1] - childrenWithCandies[i]
                    childrenWithCandies[i] = childrenWithCandies[i - 1] + 1
                    distCandies++
                }
            } else if (ratings.size > 2 && i != 0 && i != ratings.lastIndex && (ratings[i] > ratings[i - 1] || ratings[i] > ratings[i + 1])) {
                if (ratings[i] > ratings[i - 1] && childrenWithCandies[i] <= childrenWithCandies[i - 1]) {
                    distCandies += childrenWithCandies[i - 1] - childrenWithCandies[i]
                    childrenWithCandies[i] = childrenWithCandies[i - 1] + 1
                    distCandies++
                }
                if (ratings[i] > ratings[i + 1] && childrenWithCandies[i] <= childrenWithCandies[i + 1]) {
                    distCandies += childrenWithCandies[i + 1] - childrenWithCandies[i]
                    childrenWithCandies[i] = childrenWithCandies[i + 1] + 1
                    distCandies++
                }
            }
        }
        return distCandies
    }
}