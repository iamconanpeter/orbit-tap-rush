package com.iamconanpeter.orbittaprush

import kotlin.math.PI

class OrbitGameEngine(
    private val baseArcHalfWidth: Float = 0.22f,
    private val minArcHalfWidth: Float = 0.12f,
    private val baseAngularSpeed: Float = 1.25f,
    private val speedStep: Float = 0.08f,
    private val missLimit: Int = 3,
    private val orbitPeriodSec: Float = 2.6f
) {
    enum class TapResult { HIT, PERFECT_HIT, MISS, SHIELDED_MISS, CLUTCH_SAVE, GAME_OVER }

    data class Snapshot(
        val score: Int,
        val misses: Int,
        val combo: Int,
        val bestCombo: Int,
        val level: Int,
        val shieldCharges: Int,
        val clutchSaves: Int,
        val perfectHits: Int,
        val targetAngle: Float,
        val playerAngle: Float,
        val hitWindow: Float,
        val gameOver: Boolean
    )

    private var score = 0
    private var misses = 0
    private var combo = 0
    private var bestCombo = 0
    private var level = 1
    private var hitsThisLevel = 0
    private var shieldCharges = 0
    private var clutchSaves = 0
    private var perfectHits = 0
    private var targetAngle = 0f
    private var playerAngle = 0f

    init {
        advanceTarget()
    }

    fun update(deltaSec: Float) {
        if (isGameOver()) return
        val speed = currentAngularSpeed()
        playerAngle = normalizeAngle(playerAngle + deltaSec * speed)
        targetAngle = normalizeAngle(targetAngle + deltaSec * ((2.0 * PI / orbitPeriodSec).toFloat()))
    }

    fun tap(): TapResult {
        if (isGameOver()) return TapResult.GAME_OVER

        val distance = angleDistance(playerAngle, targetAngle)
        val hitWindow = currentHitWindow()
        val hit = distance <= hitWindow

        if (hit) {
            combo += 1
            bestCombo = maxOf(bestCombo, combo)
            hitsThisLevel += 1

            val perfect = distance <= hitWindow * 0.35f
            if (perfect) {
                perfectHits += 1
                score += (15 + combo * 2 + level)
            } else {
                score += (10 + combo * 2 + level)
            }

            if (combo % 5 == 0) {
                shieldCharges = minOf(2, shieldCharges + 1)
            }

            if (combo % 7 == 0) {
                clutchSaves = minOf(1, clutchSaves + 1)
            }

            maybeLevelUp()
            advanceTarget()
            return if (perfect) TapResult.PERFECT_HIT else TapResult.HIT
        }

        if (shieldCharges > 0) {
            shieldCharges -= 1
            combo = maxOf(0, combo - 1)
            return TapResult.SHIELDED_MISS
        }

        if (clutchSaves > 0 && misses == missLimit - 1) {
            clutchSaves -= 1
            combo = 0
            return TapResult.CLUTCH_SAVE
        }

        misses += 1
        combo = 0
        return TapResult.MISS
    }

    fun snapshot(): Snapshot = Snapshot(
        score = score,
        misses = misses,
        combo = combo,
        bestCombo = bestCombo,
        level = level,
        shieldCharges = shieldCharges,
        clutchSaves = clutchSaves,
        perfectHits = perfectHits,
        targetAngle = targetAngle,
        playerAngle = playerAngle,
        hitWindow = currentHitWindow(),
        gameOver = isGameOver()
    )

    fun restart() {
        score = 0
        misses = 0
        combo = 0
        bestCombo = 0
        level = 1
        hitsThisLevel = 0
        shieldCharges = 0
        clutchSaves = 0
        perfectHits = 0
        targetAngle = 0f
        playerAngle = 0f
        advanceTarget()
    }

    private fun maybeLevelUp() {
        val needed = 4 + level
        if (hitsThisLevel >= needed) {
            level += 1
            hitsThisLevel = 0
        }
    }

    private fun currentAngularSpeed(): Float =
        baseAngularSpeed + (score * speedStep * 0.03f) + ((level - 1) * 0.07f)

    private fun currentHitWindow(): Float =
        maxOf(minArcHalfWidth, baseArcHalfWidth - (level - 1) * 0.015f)

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

    internal fun forceAnglesForTest(player: Float, target: Float) {
        playerAngle = normalizeAngle(player)
        targetAngle = normalizeAngle(target)
    }

    internal fun forceMissesForTest(value: Int) {
        misses = value
    }
}
