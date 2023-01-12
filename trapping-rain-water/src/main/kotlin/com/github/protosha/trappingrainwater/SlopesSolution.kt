package com.github.protosha.trappingrainwater

import kotlin.math.min

class SlopesSolution {
    fun trap(height: IntArray): Int {
        if (height.isEmpty() || height.size == 1 || height.size == 2) {
            return 0
        }

        val slopes = ArrayList<Slope>(height.size / 2 + 1)
        var drops = 0
        while (true) {
            var isAscent = height[1] - height[0] >= 0
            var isDescent = !isAscent
            var iStart = 0
            for (i in 0 until height.lastIndex) {
                val isAscentNext = height[i + 1] - height[i] > 0
                val isDescentNext = height[i + 1] - height[i] < 0
                if ((isAscent && isDescentNext) || (isDescent && isAscentNext)) {
                    slopes.add(if (isAscent) Ascent(iStart, i, height) else Descent(iStart, i, height))
                    isAscent = isAscentNext
                    isDescent = isDescentNext
                    iStart = i
                }
            }
            slopes.add(if (isAscent) Ascent(iStart, height.lastIndex, height) else Descent(iStart, height.lastIndex, height))
            if (slopes.firstOrNull()?.isAscent == true) {
                slopes.removeAt(0)
            }
            if (slopes.lastOrNull()?.isAscent == false) {
                slopes.removeAt(slopes.lastIndex)
            }
            if (slopes.size == 0) {
                break
            }
            for ((descent, ascent) in slopes.chunked(2) { Pair(it[0], it[1]) }) {
                val topDescent = descent.first
                val topAscent = ascent.last
                val lesserTop = min(topDescent, topAscent)
                for (i in descent.iStart + 1 until descent.iEnd) {
                    if (descent.height[i] < lesserTop && descent.height[i] < topDescent) {
                        drops += lesserTop - descent.height[i]
                        descent.height[i] = lesserTop
                    }
                }
                for (i in ascent.iStart until ascent.iEnd) {
                    if (ascent.height[i] < lesserTop && ascent.height[i] < topAscent) {
                        drops += lesserTop - ascent.height[i]
                        ascent.height[i] = lesserTop
                    }
                }
            }
            slopes.clear()
        }
        return drops
    }
}

abstract class Slope(val iStart: Int, val iEnd: Int, val height: IntArray) {
    val first get() = height[iStart]
    val last get() = height[iEnd]

    abstract val isAscent: Boolean

    override fun toString(): String {
        return height.slice(iStart..iEnd).toString()
    }
}

class Ascent(iStart: Int, iEnd: Int, height: IntArray): Slope(iStart, iEnd, height) {
    override val isAscent: Boolean
        get() = true
}

class Descent(iStart: Int, iEnd: Int, height: IntArray): Slope(iStart, iEnd, height) {
    override val isAscent: Boolean
        get() = false
}
