package com.github.d_costa.acacia

data class BNode<T>(
    val value: T,
    val left: BNode<T>? = null,
    val right: BNode<T>? = null
)

/**
 * A Binary Tree
 *
 * Supports insertion, deletion, and iteration
 */
class BinaryTree<T : Comparable<T>>: Iterable<T> {

    var root: BNode<T>? = null

    fun insert(value: T) {
        root = internalInsert(root, value)
    }

    fun exists(value: T): Boolean = findNode(root, value) != null

    fun find(value: T): BNode<T>? = findNode(root, value)

    fun delete(value: T) {
        root = deleteNode(root, value)
    }

    override fun iterator(): Iterator<T> = BinaryTreeIterator(root)
}

class BinaryTreeIterator<T : Comparable<T>>(private val bt: BNode<T>?) : Iterator<T> {

    private val queue = ArrayDeque<BNode<T>>()

    init {
        var current = bt
        while(current != null) {
            queue.add(current)
            current = current.left
        }
    }

    override fun hasNext(): Boolean {
        return queue.isNotEmpty()
    }

    override fun next(): T {
        if (!hasNext()) throw RuntimeException()

        val current = queue.removeLast()

        var next = current.right
        if(next != null) {
            queue.add(next)
            next = next.left
            while(next != null) {
                queue.add(next)
                next = next.left
            }
        }

        return current.value
    }

}

private fun <T : Comparable<T>> internalInsert(tree: BNode<T>?, value: T): BNode<T> {
    return if (tree == null) {
        BNode(value)
    } else if (tree.value > value) {
        BNode(tree.value, internalInsert(tree.left, value), tree.right)
    } else if (tree.value < value) {
        BNode(tree.value, tree.left, internalInsert(tree.right, value))
    } else {
        BNode(tree.value, tree.left, tree.right)
    }
}

private tailrec fun <T : Comparable<T>> findNode(tree: BNode<T>?, value: T): BNode<T>? {
    if (tree == null) {
       return null
    }

    return if (tree.value > value) {
        findNode(tree.left, value)
    } else if (tree.value < value) {
        findNode(tree.right, value)
    } else {
        tree
    }
}

private fun <T: Comparable<T>> deleteNode(tree: BNode<T>?, value: T): BNode<T>? {
    if (tree == null) {
        return tree
    }
    var newValue = tree.value
    var leftSubtree = tree.left
    var rightSubtree = tree.right

    if (tree.value > value) {
        leftSubtree = deleteNode(tree.left, value)
    } else if (tree.value < value) {
        rightSubtree = deleteNode(tree.right, value)
    } else {
        // Found it!
        if (tree.left == null && tree.right == null) return null

        if (tree.right != null) {
            newValue = findSmallest(tree.right)
            rightSubtree = deleteNode(tree.right, newValue)
        } else if (tree.left != null) {
            newValue = findLargest(tree.left)
            leftSubtree = deleteNode(tree.left, newValue)
        }
    }
    return BNode(newValue, leftSubtree, rightSubtree)
}
private tailrec fun <T: Comparable<T>> findLargest(tree: BNode<T>): T {
    return if (tree.right != null) {
        findLargest(tree.right)
    } else {
        tree.value
    }
}

private tailrec fun <T: Comparable<T>> findSmallest(tree: BNode<T>): T {
    return if (tree.left != null) {
        findSmallest(tree.left)
    } else {
        tree.value
    }
}
