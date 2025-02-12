package com.github.protosha.majorityelement

import java.util.Arrays

object MajorityElement {
    @JvmStatic
    fun main(args: Array<String>) {
        Solution().majorityElement(intArrayOf(1,3,1,1,4,1,1,5,1,1,6,2,2))
    }
}

// optimized solution using Boyer-Moore voting algo
class OptimalSolution {
    fun majorityElement(nums: IntArray): Int {
        var count = 0
        var candidate = -1
        for (num in nums) {
            if (count == 0) {
                candidate = num
                count = 1
            } else if (candidate == num) {
                count++
            } else {
                count--
            }
        }
        return candidate
    }
}

// more universal solution fitting for K most frequent case
class Solution {
    fun majorityElement(nums: IntArray): Int {
        val frequencies = mutableMapOf<Int, Int>()
        for (num in nums) {
            if (!frequencies.contains(num)) {
                frequencies[num] = 1
            } else {
                frequencies[num] = frequencies[num]!! + 1
            }
        }
        val heap = PriorityQueue(frequencies.size) { 0 }
        for ((key, value) in frequencies) {
            heap.push(PriorityQueue.Item(value, key))
        }
        return heap.pop().value
    }
}

class PriorityQueue<T>(bufferSize: Int, private val init: () -> T) {
    var size: Int = 0
        private set
    var heap: MutableList<Item<T>> = MutableList(bufferSize) { Item(Int.MIN_VALUE, init()) }

    companion object {
        fun leftChildIndex(nodeIndex: Int) = nodeIndex * 2 + 1
        fun rightChildIndex(nodeIndex: Int) = nodeIndex * 2 + 2
        fun parentIndex(nodeIndex: Int) = (nodeIndex - 1) / 2
    }

    fun pop(): Item<T> {
        if (size == 0) {
            return Item(Int.MIN_VALUE, init())
        }
        val top = heap[0]
        heap[0] = Item(Int.MIN_VALUE, init())
        heapify(0)
        return top
    }

    fun push(value: Item<T>) {
        if (size == heap.size) {
            heap.add(value)
        } else {
            heap[size] = value
        }
        size++
        var currentIndex = size - 1
        while (parentIndex(currentIndex) >= 0 && heap[parentIndex(currentIndex)].priority < heap[currentIndex].priority) {
            heapify(parentIndex(currentIndex))
            currentIndex = parentIndex(currentIndex)
        }
    }

    private fun heapify(nodeIndex: Int) {
        var currentIndex = nodeIndex
        val currentValue = heap[currentIndex]
        val lastNonLeafIndex = parentIndex(size - 1)
        while (currentIndex <= lastNonLeafIndex) {
            var bestIndex = currentIndex
            val leftChildIndex = leftChildIndex(currentIndex)
            val rightChildIndex = rightChildIndex(currentIndex)
            if (leftChildIndex < size && heap[bestIndex].priority < heap[leftChildIndex].priority) {
                bestIndex = leftChildIndex
            }
            if (rightChildIndex < size && heap[bestIndex].priority < heap[rightChildIndex].priority) {
                bestIndex = rightChildIndex
            }
            if (bestIndex == currentIndex) {
                break;
            }
            heap[currentIndex] = heap[bestIndex]
            heap[bestIndex] = currentValue
            currentIndex = bestIndex
        }
    }

    data class Item<T>(val priority: Int, val value: T)
}