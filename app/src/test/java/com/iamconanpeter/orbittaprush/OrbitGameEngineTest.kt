package com.iamconanpeter.orbittaprush

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class OrbitGameEngineTest {

    @Test
    fun `hit increases score and combo`() {
        val engine = OrbitGameEngine(arcHalfWidth = 10f) // force easy hit
        val before = engine.snapshot()

        val hit = engine.tap()
        val after = engine.snapshot()

        assertTrue(hit)
        assertTrue(after.score > before.score)
        assertEquals(1, after.combo)
        assertEquals(0, after.misses)
    }

    @Test
    fun `three misses trigger game over`() {
        val engine = OrbitGameEngine(arcHalfWidth = 0.00001f)

        repeat(3) {
            val result = engine.tap()
            assertFalse(result)
        }

        assertTrue(engine.snapshot().gameOver)
    }

    @Test
    fun `restart clears state`() {
        val engine = OrbitGameEngine(arcHalfWidth = 0.00001f)
        repeat(3) { engine.tap() }
        assertTrue(engine.snapshot().gameOver)

        engine.restart()
        val snap = engine.snapshot()

        assertEquals(0, snap.score)
        assertEquals(0, snap.combo)
        assertEquals(0, snap.misses)
        assertFalse(snap.gameOver)
    }
}
