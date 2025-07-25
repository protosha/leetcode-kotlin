package com.github.protosha.symmetrictree

import com.github.protosha.utils.extension.joinToPrettyString
import com.github.protosha.utils.tree.TreeNode
import com.github.protosha.utils.tree.toTreeNode

object SymmetricTree {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = listOf(1, 2, 2, null, 3, null, 3)
        println("Input: ${input.joinToPrettyString()}")
        val output = Solution().isSymmetric(input.toTreeNode())
        println("Output: $output")
    }
}

class Solution {
    fun isSymmetric(root: TreeNode?): Boolean {
        if (root == null) {
            return true
        }
        return checkSymmetric(root.left, root.right)
    }

    private fun checkSymmetric(me: TreeNode?, sibling: TreeNode?): Boolean {
        if (me == null && sibling == null) {
            return true
        }
        if (me?.`val` != sibling?.`val`) {
            return false
        }
        return checkSymmetric(me?.left, sibling?.right)
            && checkSymmetric(me?.right, sibling?.left)
    }
}