package io.github.lkleno.mobilesensing.layout

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.View
import android.widget.FrameLayout

class AR(private var context: Context, private var arView: FrameLayout)
{
    private var paint = Paint()
    private var colors = arrayOf(
        Color.CYAN,
        Color.GREEN,
        Color.MAGENTA,
        Color.RED,
        Color.YELLOW
    )
    private var colorIndex = 0

    fun enableAR()
    {

    }

    fun disableAR()
    {

    }

    private fun drawRect()
    {
        val sd = ShapeDrawable(RectShape())
        sd.paint.color = Color.YELLOW
        sd.paint.style = Paint.Style.STROKE
        sd.paint.strokeWidth = 20f
        val shapeView = View(context)
        shapeView.background = sd

        val params = FrameLayout.LayoutParams(100, 100)
        params.setMargins(0, 0, 100, 100)
        arView.addView(shapeView, params)
    }
}