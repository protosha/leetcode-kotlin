package com.github.protosha.utils.extension

import com.github.protosha.utils.collection.IntArraySlice

fun IntArray.sliceRef(offset: Int = 0, count: Int = size) = IntArraySlice(this, offset, count)

fun IntArray.joinToPrettyString(): String = joinToString(separator = ", ", prefix = "[", postfix = "]") { it.toString() }