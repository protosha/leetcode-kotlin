package com.github.protosha.utils.tree

import java.util.LinkedList

class TreeNode(val `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

fun List<Int?>.toTreeNode(nodeIndex: Int = 0): TreeNode? {
    if (nodeIndex >= size) {
        return null
    }
    if (this[nodeIndex] == null) {
        return null
    }
    val node = TreeNode(this[nodeIndex]!!)
    node.left = toTreeNode(nodeIndex * 2 + 1)
    node.right = toTreeNode(nodeIndex * 2 + 2)
    return node
}

fun TreeNode?.toIntList(): List<Int?> {
    if (this == null) {
        return emptyList()
    }
    val stack = LinkedList<Pair<Int, TreeNode>>()
    val list = mutableListOf<Pair<Int, TreeNode>>()
    stack.push(Pair(0, this))
    while (stack.isNotEmpty()) {
        val operation = stack.removeLast()
        if (operation.second.left != null) {
            stack.push(Pair(operation.first * 2 + 1, operation.second.left!!))
        }
        if (operation.second.right != null) {
            stack.push(Pair(operation.first * 2 + 2, operation.second.right!!,))
        }
        list.add(operation)
    }
    val imperfectTree = MutableList<Int?>(list.maxBy { it.first }.first + 1) { null }
    list.forEachIndexed { i, item ->
        imperfectTree[item.first] = item.second.`val`
    }
    return imperfectTree
}