package com.github.protosha.hindex

object HIndex {
    @JvmStatic
    fun main(args: Array<String>) {
        val hIndex = Solution().hIndex(intArrayOf(3, 0, 6, 1, 5))
        println(hIndex)
    }
}

class Solution {
    fun hIndex(citations: IntArray): Int {
        val paperCitations = citations.sorted()
        var hPaperId = 0
        for (paperId in paperCitations.indices) {
            hPaperId = paperId
            if (paperCitations[hPaperId] >= paperCitations.size - hPaperId) {
                break
            }
        }
        return if (paperCitations[hPaperId] > paperCitations.size - hPaperId) {
            paperCitations.size - hPaperId
        } else {
            paperCitations[hPaperId]
        }
    }
}