package com.iamconanpeter.orbittaprush

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class OrbitGameEngineTest {

    @Test
    fun `perfect hit increases score combo and perfect counter`() {
        val engine = OrbitGameEngine(baseArcHalfWidth = 10f)
        engine.forceAnglesForTest(player = 0f, target = 0f)

        val result = engine.tap()
        val snap = engine.snapshot()

        assertEquals(OrbitGameEngine.TapResult.PERFECT_HIT, result)
        assertEquals(1, snap.combo)
        assertEquals(1, snap.perfectHits)
        assertTrue(snap.score >= 15)
    }

    @Test
    fun `combo streak grants shield and shielded miss does not add miss count`() {
        val engine = OrbitGameEngine(baseArcHalfWidth = 0.2f)

        repeat(5) {
            engine.forceAnglesForTest(player = 0f, target = 0f)
            engine.tap()
        }
        assertTrue(engine.snapshot().shieldCharges >= 1)

        engine.forceAnglesForTest(player = 0f, target = 3.14f)
        val missResult = engine.tap()
        val after = engine.snapshot()

        assertEquals(OrbitGameEngine.TapResult.SHIELDED_MISS, missResult)
        assertEquals(0, after.misses)
    }

    @Test
    fun `combo seven grants clutch save token`() {
        val engine = OrbitGameEngine(baseArcHalfWidth = 10f)

        repeat(7) {
            engine.forceAnglesForTest(player = 0f, target = 0f)
            engine.tap()
        }

        assertEquals(1, engine.snapshot().clutchSaves)
    }

    @Test
    fun `clutch save prevents game over on lethal miss`() {
        val engine = OrbitGameEngine(baseArcHalfWidth = 10f)

        repeat(7) {
            engine.forceAnglesForTest(player = 0f, target = 0f)
            engine.tap()
        }
        engine.forceMissesForTest(2)

        engine.forceAnglesForTest(player = 0f, target = 3.14f)
        val result = engine.tap()
        val after = engine.snapshot()

        assertEquals(OrbitGameEngine.TapResult.CLUTCH_SAVE, result)
        assertEquals(2, after.misses)
        assertEquals(0, after.clutchSaves)
        assertFalse(after.gameOver)
    }

    @Test
    fun `level up shrinks hit window over time`() {
        val engine = OrbitGameEngine(baseArcHalfWidth = 0.22f)
        val initialWindow = engine.snapshot().hitWindow

        repeat(6) {
            engine.forceAnglesForTest(player = 0f, target = 0f)
            engine.tap()
        }

        val after = engine.snapshot()
        assertTrue(after.level >= 2)
        assertTrue(after.hitWindow < initialWindow)
    }

    @Test
    fun `restart clears progression state`() {
        val engine = OrbitGameEngine(baseArcHalfWidth = 10f)
        repeat(7) {
            engine.forceAnglesForTest(player = 0f, target = 0f)
            engine.tap()
        }
        assertTrue(engine.snapshot().combo > 0)

        engine.restart()
        val snap = engine.snapshot()

        assertEquals(0, snap.score)
        assertEquals(0, snap.combo)
        assertEquals(1, snap.level)
        assertEquals(0, snap.shieldCharges)
        assertEquals(0, snap.clutchSaves)
        assertEquals(0, snap.perfectHits)
        assertFalse(snap.gameOver)
    }
}
