package com.iamconanpeter.orbittaprush

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class OrbitRushView(context: Context) : View(context) {
    private val engine = OrbitGameEngine()

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
        textSize = 52f
    }
    private val subTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#D1D5DB")
        textSize = 36f
    }

    private var lastNanos = 0L

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
        val radius = min(width, height) * 0.32f

        canvas.drawCircle(cx, cy, radius, ringPaint)

        val s = engine.snapshot()

        drawDot(canvas, cx, cy, radius, s.targetAngle, 18f, targetPaint)
        drawDot(canvas, cx, cy, radius, s.playerAngle, 20f, playerPaint)

        canvas.drawText("Score: ${s.score}", 40f, 90f, textPaint)
        canvas.drawText("Combo: ${s.combo}", 40f, 140f, subTextPaint)
        canvas.drawText("Misses: ${s.misses}/3", 40f, 185f, subTextPaint)

        if (s.gameOver) {
            canvas.drawText("Game Over", cx - 130f, cy - 10f, textPaint)
            canvas.drawText("Tap to restart", cx - 120f, cy + 45f, subTextPaint)
        } else {
            canvas.drawText("Tap when yellow meets green", cx - 240f, height - 80f, subTextPaint)
        }

        postInvalidateOnAnimation()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val snap = engine.snapshot()
            if (snap.gameOver) {
                engine.restart()
            } else {
                engine.tap()
            }
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun drawDot(canvas: Canvas, cx: Float, cy: Float, radius: Float, angle: Float, size: Float, paint: Paint) {
        val x = cx + cos(angle) * radius
        val y = cy + sin(angle) * radius
        canvas.drawCircle(x.toFloat(), y.toFloat(), size, paint)
    }
}
