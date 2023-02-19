package com.github.d_costa.acacia

@DslMarker
annotation class TreeMarker

@TreeMarker
sealed class Node<T>(open val value: T)

data class Tree<T>(override val value: T) : Node<T>(value) {
    internal val children = mutableListOf<Node<T>>()

    fun tree(value: T, init: Tree<T>.() -> Unit) {
        val t = Tree(value)
        t.init()
        children.add(t)
    }

    fun tree(subTree: Node<T>) {
        children.add(subTree)
    }

    fun leaf(value: T) {
        val l = Leaf(value)
        children.add(l)
    }

    override fun toString(): String {
        return "Tree(value=${value}, children=[${children.joinToString(", ") { it.toString() }}])"
    }
}

data class Leaf<T>(override val value: T) : Node<T>(value)

fun <T> root(value: T, init: Tree<T>.() -> Unit): Tree<T> {
    val node = Tree(value)
    node.init()
    return node
}
