package com.iamconanpeter.orbittaprush

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class OrbitRushView(context: Context) : View(context) {
    private val engine = OrbitGameEngine()
    private val prefs = context.getSharedPreferences("orbit_tap_rush", Context.MODE_PRIVATE)

    private val bgPaint = Paint().apply { color = Color.parseColor("#111827") }
    private val ringPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 24f
        color = Color.parseColor("#374151")
    }
    private val targetPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#34D399") }
    private val playerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#FBBF24") }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 46f
    }
    private val subTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#D1D5DB")
        textSize = 31f
    }
    private val pulseTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#93C5FD")
        textSize = 34f
    }

    private var lastNanos = 0L
    private var feedbackText = "Tap when yellow meets green"
    private val dailyTarget = 180 + (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) % 7) * 20

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val now = System.nanoTime()
        if (lastNanos != 0L) {
            val deltaSec = (now - lastNanos) / 1_000_000_000f
            engine.update(deltaSec.coerceAtMost(0.05f))
        }
        lastNanos = now

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        val cx = width / 2f
        val cy = height / 2f
        val radius = min(width, height) * 0.30f

        canvas.drawCircle(cx, cy, radius, ringPaint)

        val s = engine.snapshot()

        drawDot(canvas, cx, cy, radius, s.targetAngle, 18f, targetPaint)
        drawDot(canvas, cx, cy, radius, s.playerAngle, 20f, playerPaint)

        val bestScore = prefs.getInt("best_score", 0)
        val missionDone = s.score >= dailyTarget

        canvas.drawText("Score: ${s.score}", 34f, 84f, textPaint)
        canvas.drawText("Best: $bestScore", 34f, 124f, subTextPaint)
        canvas.drawText("Lv ${s.level}  Combo ${s.combo}  Shield ${s.shieldCharges}", 34f, 164f, subTextPaint)
        canvas.drawText("Misses: ${s.misses}/3  Perfect: ${s.perfectHits}  Clutch: ${s.clutchSaves}", 34f, 202f, subTextPaint)
        canvas.drawText("Daily Target: $dailyTarget ${if (missionDone) "✓" else ""}", 34f, 240f, pulseTextPaint)

        if (s.gameOver) {
            canvas.drawText("Game Over", cx - 125f, cy - 10f, textPaint)
            canvas.drawText("Tap to restart", cx - 120f, cy + 36f, subTextPaint)
        } else {
            canvas.drawText(feedbackText, 34f, height - 80f, subTextPaint)
        }

        postInvalidateOnAnimation()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val snap = engine.snapshot()
            if (snap.gameOver) {
                persistBest(snap.score)
                engine.restart()
                feedbackText = "Fresh run. Hit streak for shields"
            } else {
                when (engine.tap()) {
                    OrbitGameEngine.TapResult.PERFECT_HIT -> feedbackText = "Perfect hit! +bonus"
                    OrbitGameEngine.TapResult.HIT -> feedbackText = "Hit! Build combo"
                    OrbitGameEngine.TapResult.SHIELDED_MISS -> feedbackText = "Shield saved you"
                    OrbitGameEngine.TapResult.CLUTCH_SAVE -> feedbackText = "Clutch save! Keep focus"
                    OrbitGameEngine.TapResult.MISS -> feedbackText = "Missed — careful"
                    OrbitGameEngine.TapResult.GAME_OVER -> feedbackText = "Run over"
                }
                persistBest(engine.snapshot().score)
            }
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun persistBest(score: Int) {
        val best = prefs.getInt("best_score", 0)
        if (score > best) {
            prefs.edit().putInt("best_score", score).apply()
        }
    }

    private fun drawDot(canvas: Canvas, cx: Float, cy: Float, radius: Float, angle: Float, size: Float, paint: Paint) {
        val x = cx + cos(angle) * radius
        val y = cy + sin(angle) * radius
        canvas.drawCircle(x.toFloat(), y.toFloat(), size, paint)
    }
}
