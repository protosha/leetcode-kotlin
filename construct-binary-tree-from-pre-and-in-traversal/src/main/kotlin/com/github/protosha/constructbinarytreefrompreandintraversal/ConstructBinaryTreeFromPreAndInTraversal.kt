package com.github.protosha.constructbinarytreefrompreandintraversal

import com.github.protosha.utils.extension.joinToPrettyString
import com.github.protosha.utils.tree.TreeNode
import com.github.protosha.utils.tree.toIntList
import kotlin.math.min

object ConstructBinaryTreeFromPreAndInTraversal {
    @JvmStatic
    fun main(args: Array<String>) {
        val preorder = intArrayOf(3,9,20,15,7)
        val inorder = intArrayOf(9,3,15,20,7)
        println("Input: preorder = ${preorder.joinToPrettyString()}, inorder = ${inorder.joinToPrettyString()}")
        val output = OptimalSolution().buildTree(preorder, inorder).toIntList()
        println("Output: ${output.joinToPrettyString()}")
    }
}

// stole this one from leetcode solutions to better understand how it works
class OptimalSolution {
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        val preorderToInorder = IntArray(preorder.size) { i -> inorder.indexOf(preorder[i]) }
        var preorderSubtreeRootIndex = 0

        fun build(leftInorderIndex: Int, rightInorderIndex: Int): TreeNode? {
            if (leftInorderIndex > rightInorderIndex) {
                return null
            }
            val subtreeRoot = TreeNode(preorder[preorderSubtreeRootIndex])
            val inorderRootIndex = preorderToInorder[preorderSubtreeRootIndex]
            preorderSubtreeRootIndex++
            subtreeRoot.left = build(leftInorderIndex, inorderRootIndex - 1)
            subtreeRoot.right = build(inorderRootIndex + 1, rightInorderIndex)
            return subtreeRoot
        }

        return build(0, preorder.lastIndex)
    }
}

class NaiveSolution {
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        return buildNode(
            Int.MAX_VALUE,
            Int.MAX_VALUE,
            0,
            inorder.indexOf(preorder[0]),
            preorder,
            inorder,
            IntArray(preorder.size) { i -> inorder.indexOf(preorder[i]) }
        )
    }

    private fun buildNode(
        rightSideAncestorIndex: Int,
        parentInorderIndex: Int,
        currentPreorderIndex: Int,
        currentInorderIndex: Int,
        preorder: IntArray,
        inorder: IntArray,
        preorderToInorder: IntArray,
    ): TreeNode {
        val node = TreeNode(preorder[currentPreorderIndex])
        val newRightAncestorIndex = if (parentInorderIndex - currentInorderIndex > 0) {
            min(rightSideAncestorIndex, parentInorderIndex)
        } else {
            rightSideAncestorIndex
        }
        node.left = buildLeft(
            newRightAncestorIndex,
            currentPreorderIndex,
            currentInorderIndex,
            preorder,
            inorder,
            preorderToInorder,
        )
        node.right = buildRight(
            newRightAncestorIndex,
            currentPreorderIndex,
            currentInorderIndex,
            preorder,
            inorder,
            preorderToInorder,
        )
        return node
    }

    private fun buildLeft(
        rightInorderAncestorIndex: Int,
        parentPreorderIndex: Int,
        parentInorderIndex: Int,
        preorder: IntArray,
        inorder: IntArray,
        preorderToInorder: IntArray,
    ): TreeNode? {
        for (preorderIndex in parentPreorderIndex + 1 .. preorder.lastIndex) {
            val inorderIndex = preorderToInorder[preorderIndex]
            if (inorderIndex < parentInorderIndex) {
                return buildNode(
                    rightInorderAncestorIndex,
                    parentInorderIndex,
                    preorderIndex,
                    inorderIndex,
                    preorder,
                    inorder,
                    preorderToInorder,
                )
            }
        }
        return null
    }

    private fun buildRight(
        rightInorderAncestorIndex: Int,
        parentPreorderIndex: Int,
        parentInorderIndex: Int,
        preorder: IntArray,
        inorder: IntArray,
        preorderToInorder: IntArray,
    ): TreeNode? {
        for (preorderIndex in parentPreorderIndex + 1 .. preorder.lastIndex) {
            val inorderIndex = preorderToInorder[preorderIndex]
            if (rightInorderAncestorIndex != Int.MAX_VALUE) {
                if (inorderIndex in parentInorderIndex + 1 until rightInorderAncestorIndex) {
                    return buildNode(
                        rightInorderAncestorIndex,
                        parentInorderIndex,
                        preorderIndex,
                        inorderIndex,
                        preorder,
                        inorder,
                        preorderToInorder,
                    )
                }
            } else {
                if (inorderIndex > parentInorderIndex) {
                    return buildNode(
                        rightInorderAncestorIndex,
                        parentInorderIndex,
                        preorderIndex,
                        inorderIndex,
                        preorder,
                        inorder,
                        preorderToInorder,
                    )
                }
            }
        }
        return null
    }
}