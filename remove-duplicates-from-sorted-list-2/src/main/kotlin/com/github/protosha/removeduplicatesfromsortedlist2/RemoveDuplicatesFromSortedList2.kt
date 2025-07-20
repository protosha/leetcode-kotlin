package com.github.protosha.removeduplicatesfromsortedlist2

object RemoveDuplicatesFromSortedList2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = linkedListOf(1, 2, 2, 2, 3, 5, 5, 5)
        println(input)
        val output = Solution().deleteDuplicates(input)
        println(output)
    }

    fun linkedListOf(vararg nodeValues: Int): ListNode? {
        if (nodeValues.isEmpty()) {
            return null
        }
        return ListNode(
            nodeValues.first(),
            linkedListOf(*nodeValues.drop(1).toIntArray())
        )
    }
}

/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */
class Solution {
    fun deleteDuplicates(head: ListNode?): ListNode? {
        var prev: ListNode? = null
        var curr = head
        var newHead = head
        while (curr != null) {
            val newCurr = doDeleteDuplicates(prev, curr)
            if (prev == null) {
                newHead = newCurr
                if (newHead == null) {
                    return newHead
                }
            }
            if (newCurr === curr) {
                prev = curr
                curr = curr.next
            } else {
                curr = newCurr
            }
        }
        return newHead
    }

    private fun doDeleteDuplicates(prev: ListNode?, curr: ListNode?): ListNode? {
        var next: ListNode? = curr
        var duplicatesDetected = false
        while (next != null && next.`val` == next.next?.`val`) {
            val nextNext = next.next
            next.next = null
            next = nextNext
            duplicatesDetected = true
        }
        if (duplicatesDetected) { // values with duplicates should be removed from linked list entirely
            val nextNext = next?.next
            next?.next = null
            next = nextNext
        }
        prev?.next = next
        return next
    }
}

data class ListNode(
    val `val`: Int,
    var next: ListNode?
) {
    override fun toString(): String {
        var next: ListNode? = this
        val stringBuilder = StringBuilder().append("[")
        while (next != null) {
            stringBuilder.append(next.`val`)
            if (next.next != null) {
                stringBuilder.append(" -> ")
            }
            next = next.next
        }
        stringBuilder.append("]")
        return stringBuilder.toString()
    }
}