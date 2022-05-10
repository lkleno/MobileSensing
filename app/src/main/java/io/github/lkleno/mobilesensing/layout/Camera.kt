package io.github.lkleno.mobilesensing.layout

import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.CountDownTimer
import android.widget.FrameLayout
import android.widget.TextView
import androidx.camera.core.ImageProxy
import io.github.lkleno.mobilesensing.MainActivity
import io.github.lkleno.mobilesensing.R
import io.github.lkleno.mobilesensing.tensorflow.Detector
import org.tensorflow.lite.task.vision.detector.Detection
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class Camera(private var context: MainActivity, private var arView: FrameLayout, private var detector: Detector, private var audio : Audio)
{
    private var detections : MutableList<Detection> = mutableListOf()
    private var oldDetections : MutableList<Detection> = mutableListOf()
    private var detectionsColor : MutableList<Int> = mutableListOf()
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
    private val maxPercentageDifference = 0.15f
    private var halfWidth : Float = 0f
    private var halfHeight : Float = 0f
    private var xMaxDifference : Float = 0f
    private var yMaxDifference : Float = 0f
    //modelImageSize is the input size of the model (can be seen in model.tflite)
    private val modelImageSize : Int = 320
    private var windowMul : Int = 0
    //modelXDrift is to compensate the drift found in the detectors x-axis
    private val modelXDrift = -50
    private var lastAudioTime : Long = 0L
    private val audioInterval : Int = 10000
    private lateinit var detectItem : String
    private lateinit var centreRegionCorners : Array<Array<Float>>

    fun enableBoxes(detectItem : String)
    {
        this.detectItem = detectItem

        halfWidth = arView.width / 2f
        halfHeight = arView.height / 2f

        windowMul = arView.width / modelImageSize

        xMaxDifference = arView.width * maxPercentageDifference
        yMaxDifference = arView.height * maxPercentageDifference

        val xThird = arView.width * 0.33f
        val x2Third = xThird * 2
        val yThird = arView.height * 0.33f
        val y2Third = yThird * 2
        centreRegionCorners = arrayOf(
            arrayOf(xThird, yThird),
            arrayOf(x2Third, yThird),
            arrayOf(xThird, y2Third),
            arrayOf(x2Third, y2Third))

        showBoxes = true
    }

    fun disableBoxes()
    {
        showBoxes = false

        //Wait a bit before removing all the bounding boxes
        object : CountDownTimer(200, 200) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                arView.removeAllViews()
            }
        }.start()
    }

    fun attemptDetection(image : ImageProxy, maxResults : Int, scoreThreshold : Float)
    {
        if(showBoxes)
        {
            val currentTime = System.currentTimeMillis()
            val canPlayAudio = currentTime - lastAudioTime >= audioInterval
            val regions : MutableList<String> = ArrayList()

            // X and Y are inverted due to model output
            detections = detector.run(image.toBitmap(), this.detectItem, maxResults, scoreThreshold)!!

            customRunOnUIThread { arView.removeAllViews() }

            for((i, detection) in detections.withIndex())
            {
                val box = detection.boundingBox

                var isNewDetection = true
                for(oldDetection in oldDetections)
                {
                    val oldBox = oldDetection.boundingBox


                    if(abs(box.centerY() - oldBox.centerY()) <= xMaxDifference &&
                        abs(box.centerX() - oldBox.centerX()) <= yMaxDifference &&
//                        abs(box.height() - oldBox.height()) <= xMaxDifference &&
//                        abs(box.width() - oldBox.width()) <= yMaxDifference &&
                        detection.categories[0].label == oldDetection.categories[0].label)
                    {
                        isNewDetection = false

                        //Optimizations
                        oldDetections.remove(oldDetection)
                        break
                    }
                }

                if(isNewDetection)
                {
                    detectionsColor.add(i, colors[colorIndex])

                    colorIndex++
                    if(colorIndex >= colors.size) colorIndex = 0
                }

                if(canPlayAudio)
                {
                    regions.add(getDetectionRegion((((box.top + box.bottom) / 2).toInt() + modelXDrift) * windowMul,
                        ((box.left + box.right) / 2).toInt() * windowMul))
                }

                drawRect(detection.categories[0].label, box.top.toInt(), box.left.toInt(), box.bottom.toInt(), box.right.toInt(), detectionsColor[i])
            }


            if(canPlayAudio && regions.isNotEmpty())
            {
                for (region in regions.distinct())
                {
                    audio.startPlayAudio(Collections.frequency(regions, region), false,
                            detections[0].categories[0].label, false,
                            region, false)
                }
                lastAudioTime = currentTime
            }

            oldDetections = detections.toMutableList()
        }
    }

    // 5 Regions
    // +--------+--------+
    // |        |        |
    // |     +--+--+     |
    // |     |     |     |
    // |     |     |     |
    // +-----+     +-----+
    // |     |     |     |
    // |     |     |     |
    // |     +--+--+     |
    // |        |        |
    // +--------+--------+
    private fun getDetectionRegion(x : Int, y : Int) : String
    {
        return if(x > halfWidth)
        {
            if(y < halfHeight)
            {
                if(x > centreRegionCorners[0][0] && y > centreRegionCorners[0][1]) context.getString(R.string.string_center)
                else context.getString(R.string.string_upperleft)
            } else {
                if(x > centreRegionCorners[2][0] && y < centreRegionCorners[2][1]) context.getString(R.string.string_center)
                else context.getString(R.string.string_lowerleft)
            }
        }
        else
        {
            if(y < halfHeight)
            {
                if(x < centreRegionCorners[1][0] && y > centreRegionCorners[1][1]) context.getString(R.string.string_center)
                else context.getString(R.string.string_upperright)
            } else {
                if(x < centreRegionCorners[3][0] && y < centreRegionCorners[3][1]) context.getString(R.string.string_center)
                else context.getString(R.string.string_lowerright)
            }
        }
    }

    private fun drawRect(label : String, x1 : Int, y1: Int, x2 : Int, y2 : Int, color : Int)
    {
        //Sets the shape properties
        val sd = ShapeDrawable(RectShape())
        sd.paint.color = color
//        sd.paint.color = colors[colorIndex]
        sd.paint.style = Paint.Style.STROKE
        sd.paint.strokeWidth = 20f
        val shapeView = TextView(context)
        shapeView.background = sd

        shapeView.textSize = 20f
        shapeView.setTextColor(color)
        shapeView.setPadding(20, 10, 20, 10)

        val windowMul = arView.width / modelImageSize
        var x1 = (x1 + modelXDrift) * windowMul
        var x2 = (x2 + modelXDrift) * windowMul
        val xOffset = (((x2 + x1) / 2) - halfWidth.toInt()) * -2
        x1 += xOffset
        x2 += xOffset
        val y1 = y1 * windowMul
        val y2 = y2 * windowMul

        //Sets the box size
        val params = FrameLayout.LayoutParams(x2 - x1, y2 - y1)
        params.setMargins(x1, y1, x2, y2)

        customRunOnUIThread{
            shapeView.text = label
            arView.addView(shapeView, params)
        }
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