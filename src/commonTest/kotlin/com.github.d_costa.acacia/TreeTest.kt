package com.github.d_costa.acacia

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TreeTest {
    @Test
    fun manuallyTestTreeDSL() {
        val t = root(10) {
            leaf(1)



            tree(20) {
                leaf(21)

                leaf( 22)

            }

            leaf(2)

            leaf(3)
        }

        root(1) {
            tree(t)
        }
        assertEquals(10, t.value)
        assertTrue { t.children.size == 4 }
        assertTrue { t.children[0] is Leaf<Int> && t.children[0].value == 1 }
        assertTrue { t.children[1] is Tree<Int> && t.children[1].value == 20 }
        assertTrue { t.children[1] is Tree<Int> && (t.children[1] as Tree<Int>).children[0].value == 21}
        assertTrue { t.children[1] is Tree<Int> && (t.children[1] as Tree<Int>).children[1].value == 22}

        assertTrue { t.children[2] is Leaf<Int> && t.children[2].value == 2 }
        assertTrue { t.children[3] is Leaf<Int> && t.children[3].value == 3 }
    }

    @Test
    fun testEmptyTree() {
        val t = root(10) {}
        assertTrue { t.children.isEmpty() }
    }

    @Test
    fun subTreeTest() {
        val t = root(10) {
            leaf(2)
        }
        val s = root(2) {
            leaf(1)
            tree(t)
            leaf(2)
        }
        assertTrue { s.children.size == 3 && s.children[1] is Tree<Int> && (s.children[1] as Tree<Int>).children[0].value == 2}
    }
}