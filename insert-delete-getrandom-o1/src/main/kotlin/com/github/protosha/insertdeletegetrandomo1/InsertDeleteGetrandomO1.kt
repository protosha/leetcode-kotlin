package com.github.protosha.insertdeletegetrandomo1

import java.util.LinkedList
import kotlin.math.abs

object InsertDeleteGetrandomO1 {
    @JvmStatic
    fun main(args: Array<String>) {

    }
}

class RandomizedSet() {
    companion object {
        private const val INITIAL_CAPACITY = 16
        private const val ENLARGE_LOAD_FACTOR = 6.5 // from GoLang

        private fun _contains(buckets: Array<MutableList<Int>>, value: Int): Boolean {
            return _pickBucket(buckets, value)?.contains(value) ?: false
        }

        private fun _insert(buckets: Array<MutableList<Int>>, value: Int) {
            val bucket = _pickBucket(buckets, value)
            if (bucket != null && !bucket.contains(value)) {
                bucket.add(value)
            }
        }

        private fun _remove(buckets: Array<MutableList<Int>>, value: Int) {
            val bucket = _pickBucket(buckets, value)
            val iv = bucket?.indexOf(value) ?: -1
            if (iv != -1) {
                bucket!!.removeAt(iv)
            }
        }

        private fun _pickBucket(buckets: Array<MutableList<Int>>, value: Int): MutableList<Int>? {
            if (buckets.isEmpty()) {
                return null
            }
            val hash = Fnv1aHash.hash(value)
            val ib = abs(hash % buckets.size)
            return buckets[ib]
        }
    }

    var size = 0
        private set

    private var buckets = emptyArray<MutableList<Int>>()
    private val loadFactor get() = if (buckets.isNotEmpty()) size / buckets.size else Int.MAX_VALUE

    fun contains(`val`: Int) = _contains(buckets, `val`)

    fun insert(`val`: Int) =
        if (contains(`val`)) {
            false
        } else {
            if (loadFactor > ENLARGE_LOAD_FACTOR) {
                enlarge()
            }
            _insert(buckets, `val`)
            size++
            true
        }

    fun remove(`val`: Int) =
        if (contains(`val`)) {
            _remove(buckets, `val`)
            size--
            true
        } else {
            false
        }

    fun getRandom(): Int {
        var randomBucket = buckets.random()
        while (randomBucket.isEmpty()) {
            randomBucket = buckets.random()
        }
        return randomBucket.random()
    }

    private fun enlarge() {
        if (buckets.isEmpty()) {
            buckets = Array(INITIAL_CAPACITY) { LinkedList() }
            return
        }

        val newBuckets = Array<MutableList<Int>>(buckets.size * 2) { LinkedList() }
        for (bucket in buckets) {
            for (value in bucket) {
                _insert(newBuckets, value)
            }
        }
        buckets = newBuckets
    }
}

object Fnv1aHash {
    fun hash(value: Int): Int = (5381 xor value) * 16777619
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * var obj = RandomizedSet()
 * var param_1 = obj.insert(`val`)
 * var param_2 = obj.remove(`val`)
 * var param_3 = obj.getRandom()
 */