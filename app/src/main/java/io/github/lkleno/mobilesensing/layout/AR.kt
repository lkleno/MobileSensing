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
    private var colors = intArrayOf(
        Color.CYAN,
        Color.GREEN,
        Color.MAGENTA,
        Color.RED,
        Color.YELLOW,
        Color.BLUE
    )
    private var colorIndex = 0

    fun enableAR()
    {
        for (i in 1..10)
        {
            drawRect((i - 1) * 100, (i - 1) * 100, i * 100, i *100)
        }
    }

    fun disableAR()
    {

    }

    private fun drawRect(x1 : Int, y1: Int, x2 : Int, y2 : Int)
    {
        //Sets the shape properties
        val sd = ShapeDrawable(RectShape())
        sd.paint.color = colors[colorIndex]
        sd.paint.style = Paint.Style.STROKE
        sd.paint.strokeWidth = 20f
        val shapeView = View(context)
        shapeView.background = sd

        colorIndex++
        if(colorIndex >= colors.size) colorIndex = 0

        //Sets the box size
        val params = FrameLayout.LayoutParams(x2 - x1, y2 - x1)
        params.setMargins(x1, y1, x2, y2)
        arView.addView(shapeView, params)
    }
}