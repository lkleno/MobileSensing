package io.github.lkleno.mobilesensing.layout

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.media.Image
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.camera.core.ImageProxy
import io.github.lkleno.mobilesensing.MainActivity
import io.github.lkleno.mobilesensing.tensorflow.Detector
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class Camera(private var context: MainActivity, private var arView: FrameLayout, private var detector: Detector)
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
    private var showBoxes = false
    //modelImageSize is the input size of the model (can be seen in model.tflite)
    private val modelImageSize = 320
    //modelXDrift is to compensate the drift found in the detectors x-axis
    private val modelXDrift = -50


    fun enableBoxes()
    {
        showBoxes = true

//        for (i in 1..10)
//        {
//            drawRect((i - 1) * 100, (i - 1) * 100, i * 100, i * 100)
//        }

    }

    fun disableBoxes()
    {
        showBoxes = false

        arView.removeAllViews()
    }

    fun attemptDetection(image : ImageProxy, maxResults : Int, scoreThreshold : Float)
    {
        if(showBoxes)
        {
            val results = detector.run(image.toBitmap(), maxResults, scoreThreshold)

            if (results != null)
            {
                customRunOnUIThread {
                    arView.removeAllViews()

                    for ((i, obj) in results.withIndex())
                    {
                        val box = obj.boundingBox

                        drawRect(box.top.toInt(), box.left.toInt(), box.bottom.toInt(), box.right.toInt())
                    }
                }
            }
        }
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

        val width = arView.width
        val windowMul = width / modelImageSize
        var x1 = (x1 + modelXDrift) * windowMul
        var x2 = (x2 + modelXDrift) * windowMul
        val xOffset = (((x2 + x1) / 2) - (width / 2)) * -2
        x1 += xOffset
        x2 += xOffset
        val y1 = y1 * windowMul
        val y2 = y2 * windowMul

        Log.d("x1", x1.toString())
        Log.d("x2", x2.toString())
        Log.d("y1", y1.toString())
        Log.d("y2", y2.toString())
        //Sets the box size
        val params = FrameLayout.LayoutParams(x2 - x1, y2 - y1)
        params.setMargins(x1, y1, x2, y2)
        arView.addView(shapeView, params)

    }

    private fun customRunOnUIThread(function : Runnable)
    {
        try {
            context.runOnUiThread(function)
        }
        catch(e : InterruptedException) {
            e.printStackTrace();
        }
    }
}

fun ImageProxy.toBitmap(): Bitmap {
    val yBuffer = planes[0].buffer // Y
    val vuBuffer = planes[2].buffer // VU

    val ySize = yBuffer.remaining()
    val vuSize = vuBuffer.remaining()

    val nv21 = ByteArray(ySize + vuSize)

    yBuffer.get(nv21, 0, ySize)
    vuBuffer.get(nv21, ySize, vuSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
    val imageBytes = out.toByteArray()
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}