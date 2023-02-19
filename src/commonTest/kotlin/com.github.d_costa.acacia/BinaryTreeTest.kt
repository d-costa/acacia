package com.github.d_costa.acacia

import kotlin.test.*

class BinaryTreeTest {
    @Test
    fun testDelete() {
       val bt = BinaryTree<Int>()
        bt.insert(20)
        bt.insert(6)
        bt.insert(25)
        bt.insert(4)
        bt.insert(8)
        bt.insert(22)
        bt.insert(30)

        bt.delete(20)

        assertNull(bt.find(20))
        assertTrue { !bt.exists(20) }
        assertContentEquals(listOf(4, 6, 8, 22, 25, 30), bt.toList())
    }

    @Test
    fun testFind() {
        val bt = BinaryTree<Int>()
        bt.insert(20)
        bt.insert(6)
        bt.insert(25)
        bt.insert(4)
        bt.insert(8)
        bt.insert(22)
        bt.insert(30)


        assertNotNull(bt.find(4))
        assertNotNull(bt.find(8))
        assertNotNull(bt.find(22))
        assertNotNull(bt.find(30))
    }


    @Test
    fun testIterator() {
        val bt = BinaryTree<Int>()
        bt.insert(20)
        bt.insert(6)
        bt.insert(25)
        bt.insert(4)
        bt.insert(8)
        bt.insert(22)
        bt.insert(30)


        val it = bt.iterator()
        assertEquals(4, it.next())
        assertEquals(6, it.next())
        assertEquals(8, it.next())
        assertEquals(20, it.next())
        assertEquals(22, it.next())
        assertEquals(25, it.next())
        assertEquals(30, it.next())
    }

    @Test
    fun testDeleteRoot() {
        val bt = BinaryTree<Int>()

        bt.insert(20)

        bt.delete(20)

        assertTrue { !bt.iterator().hasNext() }
        assertFails { bt.iterator().next() }
    }

    @Test
    fun testDeleteEmpty() {
        val bt = BinaryTree<Int>()

        bt.delete(20)

        assertTrue { !bt.iterator().hasNext() }
    }

    @Test
    fun testDeleteRight() {
        val bt = BinaryTree<Int>()
        bt.insert(1)
        bt.insert(2)

        bt.delete(1)

        assertContentEquals(listOf(2), bt.toList())
    }

    @Test
    fun testDeleteLeft() {
        val bt = BinaryTree<Int>()
        bt.insert(2)
        bt.insert(1)

        bt.delete(1)

        assertContentEquals(listOf(2), bt.toList())
    }

    @Test
    fun testDeleteWithoutRightSubtree() {
        val bt = BinaryTree<Int>()
        bt.insert(20)
        bt.insert(6)
        bt.insert(25)
        bt.insert(4)
        bt.insert(8)


        assertTrue { bt.exists(20) }
        assertTrue { bt.exists(25) }

        bt.delete(25)

        bt.delete(20)


        assertTrue { !bt.exists(25) }
        assertTrue { !bt.exists(20) }

        assertContentEquals(listOf(4, 6, 8), bt.toList())
    }

    @Test
    fun testRepeatInsert() {
        val bt = BinaryTree<Int>()

        bt.insert(20)
        bt.insert(20)

        val it = bt.iterator()
        assertEquals(20, it.next())
        assertFails { it.next() }
    }

}