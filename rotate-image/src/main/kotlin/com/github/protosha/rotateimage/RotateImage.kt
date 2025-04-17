package com.github.protosha.rotateimage

object RotateImage {
    @JvmStatic
    fun main(args: Array<String>) {
        val matrix = arrayOf(
            intArrayOf(1,2,3),
            intArrayOf(4,5,6),
            intArrayOf(7,8,9),
        )
        printMatrix(matrix)
        Solution().rotate(matrix)
        printMatrix(matrix)
    }

    fun printMatrix(m: Array<IntArray>) {
        for (row in m) {
            println(row.joinToString())
        }
    }
}

class Solution {
    fun rotate(matrix: Array<IntArray>): Unit {
        for (iRow in matrix.indices) {
            for (iCol in matrix[iRow].indices) {
                if (iRow <= iCol) {
                    val temp = matrix[iRow][iCol]
                    matrix[iRow][iCol] = matrix[iCol][iRow]
                    matrix[iCol][iRow] = temp
                }
            }
        }

        for (iRow in matrix.indices) {
            for (iCol in matrix[iRow].indices) {
                if (iCol < matrix.size / 2) {
                    val temp = matrix[iRow][iCol]
                    matrix[iRow][iCol] = matrix[iRow][matrix.size - iCol - 1]
                    matrix[iRow][matrix.size - iCol - 1] = temp
                }
            }
        }
    }
}