package com.github.protosha.invertbinarytree

import com.github.protosha.utils.extension.joinToPrettyString
import com.github.protosha.utils.tree.TreeNode
import com.github.protosha.utils.tree.toIntList
import com.github.protosha.utils.tree.toTreeNode

object InvertBinaryTree {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = listOf(4, 2, 7, 1, 3, 6, 9)
        println("Input: ${input.joinToPrettyString()}")
        val output = Solution().invertTree(input.toTreeNode()).toIntList()
        println("Output: ${output.joinToPrettyString()}")
    }
}

class Solution {
    fun invertTree(root: TreeNode?): TreeNode? {
        if (root == null) {
            return root
        }
        if (root.left == null && root.right == null) {
            return root
        }
        val temp = root.left
        root.left = root.right
        root.right = temp
        invertTree(root.left)
        invertTree(root.right)
        return root
    }
}