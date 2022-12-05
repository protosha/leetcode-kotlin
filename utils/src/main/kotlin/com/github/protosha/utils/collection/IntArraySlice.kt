package com.github.protosha.utils.collection

class IntArraySlice(private val arr: IntArray, private val offset: Int = 0, val size: Int = arr.size) {
    operator fun get(i: Int) = arr[offset + i]
    fun sliceRef(offset: Int, count: Int) = IntArraySlice(arr, offset, count)
    fun isEmpty() = size == 0
}
