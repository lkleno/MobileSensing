package io.github.lkleno.mobilesensing.layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class ARView
{
    var paint = Paint()
    var colors = arrayOf(
        Color.CYAN,
        Color.GREEN,
        Color.MAGENTA,
        Color.RED,
        Color.YELLOW
    )
    var colorIndex = 0

    fun enableAR()
    {

    }

    fun disableAR()
    {

    }
}