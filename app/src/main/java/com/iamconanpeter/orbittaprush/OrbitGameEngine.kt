package com.iamconanpeter.orbittaprush

import kotlin.math.PI

class OrbitGameEngine(
    private val arcHalfWidth: Float = 0.22f,
    private val baseAngularSpeed: Float = 1.25f,
    private val speedStep: Float = 0.08f,
    private val missLimit: Int = 3,
    private val orbitPeriodSec: Float = 2.6f
) {
    data class Snapshot(
        val score: Int,
        val misses: Int,
        val combo: Int,
        val bestCombo: Int,
        val targetAngle: Float,
        val playerAngle: Float,
        val gameOver: Boolean
    )

    private var elapsed = 0f
    private var score = 0
    private var misses = 0
    private var combo = 0
    private var bestCombo = 0
    private var targetAngle = 0f
    private var playerAngle = 0f

    init {
        advanceTarget()
    }

    fun update(deltaSec: Float) {
        if (isGameOver()) return
        elapsed += deltaSec
        val speed = currentAngularSpeed()
        playerAngle = normalizeAngle(playerAngle + deltaSec * speed)
        targetAngle = normalizeAngle(targetAngle + deltaSec * ((2.0 * PI / orbitPeriodSec).toFloat()))
    }

    fun tap(): Boolean {
        if (isGameOver()) return false
        val hit = angleDistance(playerAngle, targetAngle) <= arcHalfWidth
        if (hit) {
            combo += 1
            bestCombo = maxOf(bestCombo, combo)
            score += (10 + combo * 2)
            advanceTarget()
        } else {
            misses += 1
            combo = 0
        }
        return hit
    }

    fun snapshot(): Snapshot = Snapshot(
        score = score,
        misses = misses,
        combo = combo,
        bestCombo = bestCombo,
        targetAngle = targetAngle,
        playerAngle = playerAngle,
        gameOver = isGameOver()
    )

    fun restart() {
        elapsed = 0f
        score = 0
        misses = 0
        combo = 0
        bestCombo = 0
        targetAngle = 0f
        playerAngle = 0f
        advanceTarget()
    }

    private fun currentAngularSpeed(): Float =
        baseAngularSpeed + score * speedStep * 0.03f

    private fun isGameOver(): Boolean = misses >= missLimit

    private fun advanceTarget() {
        targetAngle = normalizeAngle(targetAngle + 1.7f)
    }

    private fun normalizeAngle(angle: Float): Float {
        var a = angle
        val full = (2.0 * PI).toFloat()
        while (a < 0f) a += full
        while (a >= full) a -= full
        return a
    }

    internal fun angleDistance(a: Float, b: Float): Float {
        val full = (2.0 * PI).toFloat()
        val diff = kotlin.math.abs(a - b)
        return minOf(diff, full - diff)
    }
}
