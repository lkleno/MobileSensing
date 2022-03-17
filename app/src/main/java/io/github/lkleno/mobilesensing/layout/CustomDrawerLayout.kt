package io.github.lkleno.mobilesensing.layout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.drawerlayout.widget.DrawerLayout

class CustomDrawerLayout : DrawerLayout
{
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onInterceptTouchEvent(ev : MotionEvent) : Boolean
    {
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev : MotionEvent) : Boolean
    {
        return false
    }
}