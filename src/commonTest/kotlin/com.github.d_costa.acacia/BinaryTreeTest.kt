package com.github.d_costa.acacia

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BinaryTreeTest {
    @Test
    fun testDelete() {
       val bt = BinaryTree<Int>()
        bt.insert(20)
        bt.insert(6)
        bt.insert(10)
        bt.insert(4)
        bt.insert(8)
        bt.insert(9)
        bt.insert(15)

        for (x in bt) {
            println(x)
        }

        bt.delete(20)

        for (x in bt) {
            println(x)
        }
    }

}