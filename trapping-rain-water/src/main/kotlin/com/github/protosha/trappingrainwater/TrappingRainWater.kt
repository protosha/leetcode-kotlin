package com.github.protosha.trappingrainwater

object TrappingRainWater {
    @JvmStatic
    fun main(args: Array<String>) {
        val height = intArrayOf(4,2,0,3,2,5)
        println(SlopesSolution().trap(height))
    }
}