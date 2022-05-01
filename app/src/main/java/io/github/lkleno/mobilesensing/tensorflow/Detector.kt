package io.github.lkleno.mobilesensing.tensorflow

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import io.github.lkleno.mobilesensing.MainActivity
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector

class Detector(private var context : MainActivity) {

    private val modelFile = "model.tflite"

    fun run(bitmap: Bitmap, allowedLabel : String, maxResults: Int, scoreThreshold: Float): MutableList<Detection>? {
        val image =  TensorImage.fromBitmap(bitmap)

        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(maxResults)
            .setLabelAllowList(mutableListOf(allowedLabel))
            .setScoreThreshold(scoreThreshold)
            .build()

        val detector = ObjectDetector.createFromFileAndOptions(
            context,
            modelFile,
            options
        )

        val results = detector.detect(image)

//        debugPrint(results)

        return results
    }

    private fun debugPrint(results : List<Detection>) {
        for ((i, obj) in results.withIndex()) {
            val box = obj.boundingBox

            Log.d(TAG, "Detected object: ${i} ")
            Log.d(TAG, "  boundingBox: (${box.left}, ${box.top}) - (${box.right},${box.bottom})")

            for ((j, category) in obj.categories.withIndex()) {
                Log.d(TAG, "    Label $j: ${category.label}")
                val confidence: Int = category.score.times(100).toInt()
                Log.d(TAG, "    Confidence: ${confidence}%")
            }
        }
    }
}