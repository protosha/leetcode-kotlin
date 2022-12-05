package com.github.protosha.medianoftwosortedarrays.extension

import com.github.protosha.utils.collection.IntArraySlice

fun IntArraySlice.median() = when {
    isEmpty() -> 0.0
    size % 2 == 0 -> (this[size / 2] + this[size / 2 - 1]) / 2.0
    else -> this[size / 2].toDouble()
}
