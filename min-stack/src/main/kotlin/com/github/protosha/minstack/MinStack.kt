package com.github.protosha.minstack

import java.util.PriorityQueue
import java.util.Stack

object MinStack {
    @JvmStatic
    fun main(args: Array<String>) {
        val ops = listOf("push","push","push","getMin","pop","top","getMin")
        println("Input ops: ${ops.joinToString(prefix = "[", postfix = "]")}")
        val args = listOf(-2, 0, -3, null, null, null, null)
        println("Input args: ${args.joinToString(prefix = "[", postfix = "]")}}")
        val minStack = MinStack()
        val results = mutableListOf<Int?>()
        for ((op, arg) in ops.zip(args)) {
            val result = when {
                op == "push" -> minStack.push(arg!!)
                op == "pop" -> minStack.pop()
                op == "top" -> minStack.top()
                op == "getMin" -> minStack.getMin()
                else -> throw Exception("Unknown operation")
            }.let { if (it is Int) it else null }
            results.add(result)
        }
        println("Output: ${results.joinToString(prefix = "[", postfix = "]")}}")
    }

    class MinStack {
        private val heap = PriorityQueue<Int>()
        private val stack = Stack<Int>()

        fun push(`val`: Int) {
            heap.add(`val`)
            stack.push(`val`)
        }

        fun pop() {
            heap.remove(stack.pop())
        }

        fun top(): Int = stack.peek()

        fun getMin(): Int = heap.peek()
    }
}