package com.github.d_costa.acacia

data class BNode<T>(
    var value: T,
    var left: BNode<T>? = null,
    var right: BNode<T>? = null
)


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

class BinaryTreeIterator<T : Comparable<T>>(val bt: BNode<T>?) : Iterator<T> {

    var current = bt
    override fun hasNext(): Boolean {
        return current != null
    }

    override fun next(): T {
        if (!hasNext() || current == null) throw RuntimeException()

        val value = current!!.value

        current = if (current?.left != null) {
            current!!.left
        } else {
            current?.right
        }

        return value
    }

}

private fun <T : Comparable<T>> internalInsert(tree: BNode<T>?, value: T): BNode<T> {
    if (tree == null) {
        return BNode(value)
    } else if (tree.value > value) {
        tree.left = internalInsert(tree.left, value)
    } else if (tree.value < value) {
        tree.right = internalInsert(tree.right, value)
    }
    return tree
}

private tailrec fun <T : Comparable<T>> findNode(tree: BNode<T>?, value: T): BNode<T>? {
    return if (tree == null) {
        null
    } else if (tree.value == value) {
        tree
    } else if (tree.value > value) {
        findNode(tree.left, value)
    } else if (tree.value < value) {
        findNode(tree.right, value)
    } else {
        null
    }
}


private fun <T: Comparable<T>> deleteNode(tree: BNode<T>?, value: T): BNode<T>? {
    if (tree == null) {
        return tree
    } else if (tree.value > value) {
        tree.left = deleteNode(tree.left, value)
    } else if (tree.value < value) {
        tree.right = deleteNode(tree.right, value)
    } else {
        // Found it!
        if (tree.left == null && tree.right == null) return null
        else if (tree.right != null) {
            tree.value = findSmallest(tree.right!!)
            tree.right = deleteNode(tree.right, tree.value)
        } else if (tree.left != null) {
            tree.value = findLargest(tree.left!!)
            tree.left = deleteNode(tree.left, tree.value)
        }
    }
    return tree
}
private tailrec fun <T: Comparable<T>> findLargest(tree: BNode<T>): T {
    return if (tree.right != null) {
        findLargest(tree.right!!)
    } else {
        tree.value
    }
}

private tailrec fun <T: Comparable<T>> findSmallest(tree: BNode<T>): T {
    return if (tree.left != null) {
        findSmallest(tree.left!!)
    } else {
        tree.value
    }
}
