package io.github.lkleno.mobilesensing

import android.Manifest
import android.R.attr.path
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.github.lkleno.mobilesensing.databinding.ActivityMainBinding
import io.github.lkleno.mobilesensing.layout.Camera
import io.github.lkleno.mobilesensing.layout.CustomDrawerLayout
import io.github.lkleno.mobilesensing.tensorflow.Detector
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


typealias CameraListener = (image : ImageProxy) -> Unit
private lateinit var binding : ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var detectionScoreThreshold = 0.35f
    private var imageCapture : ImageCapture? = null
    private var videoCapture : VideoCapture<Recorder>? = null
    private var recording : Recording? = null
    private val dictionary = mapOf("2" to  MediaPlayer.create(this,R.raw.two), "3"  to  MediaPlayer.create(this,R.raw.three),
    "4"  to  MediaPlayer.create(this,R.raw.four), "5"  to  MediaPlayer.create(this,R.raw.five), "6"  to  MediaPlayer.create(this,R.raw.six),
    "7"  to  MediaPlayer.create(this,R.raw.seven), "8"  to  MediaPlayer.create(this,R.raw.eight), "9"  to  MediaPlayer.create(this,R.raw.nine),
    "10"  to  MediaPlayer.create(this,R.raw.ten), "Clothing"  to  MediaPlayer.create(this,R.raw.clothing), "Clothings"  to  MediaPlayer.create(this,R.raw.clothings),
    "Coin"  to  MediaPlayer.create(this,R.raw.coin), "Coins"  to  MediaPlayer.create(this,R.raw.coins), "Earrings"  to  MediaPlayer.create(this,R.raw.earrings),
    "Footwear"  to  MediaPlayer.create(this,R.raw.footwear), "Glasses"  to  MediaPlayer.create(this,R.raw.glasses), "Necklace"  to  MediaPlayer.create(this,R.raw.necklace),
    "Necklaces"  to  MediaPlayer.create(this,R.raw.necklaces), "Watch"  to  MediaPlayer.create(this,R.raw.watch), "Watchs"  to  MediaPlayer.create(this,R.raw.watches),
    "Wheelchair"  to  MediaPlayer.create(this,R.raw.wheelchair), "Wheelchairs"  to  MediaPlayer.create(this,R.raw.wheelchairs), "OneUpperLeft"  to  MediaPlayer.create(this,R.raw.one_sentence_upper_left),
    "OneUpperRight"  to  MediaPlayer.create(this,R.raw.one_sentence_upper_right), "OneLowerLeft"  to  MediaPlayer.create(this,R.raw.one_sentence_lower_left), "OneLowerRight"  to  MediaPlayer.create(this,R.raw.one_sentence_lower_right),
    "OneCenter"  to  MediaPlayer.create(this,R.raw.one_sentence_center), "UpperRight"  to  MediaPlayer.create(this,R.raw.sentence_upper_right), "UpperLeft"  to  MediaPlayer.create(this,R.raw.sentence_upper_left),
    "LowerRight"  to  MediaPlayer.create(this,R.raw.sentence_lower_right), "LowerLeft"  to  MediaPlayer.create(this,R.raw.sentence_upper_left), "Center"  to  MediaPlayer.create(this,R.raw.sentence_center))

    private lateinit var menuToggle : ActionBarDrawerToggle
    private lateinit var cameraExecutor : ExecutorService
    private lateinit var view : CustomDrawerLayout
    private lateinit var camera : Camera
    private lateinit var detector : Detector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)

        flagSetup()

        // Check Camera Permissions
        if(allPermissionsGranted()) startCamera()
        else ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)

        detectorSetup()

        uiSetup()

        listenerSetup()


    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_CODE_PERMISSIONS) {
            if(allPermissionsGranted()) startCamera()
            else {
                Toast.makeText(this, "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(menuToggle.onOptionsItemSelected(item))
        {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun flagSetup()
    {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun detectorSetup()
    {
        detector = Detector(this)
    }

    private fun uiSetup()
    {
        menuToggle = ActionBarDrawerToggle(this, view, R.string.menu_open, R.string.menu_close)
        view.addDrawerListener(menuToggle)
        menuToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.maxObjectsValue.text = binding.maxObjectsSlider.progress.toString()


        camera = Camera(this, binding.arView, detector)


        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun listenerSetup()
    {
        val menu = binding.navigationView.menu
        val radioGroup = menu.getItem(0).actionView as RadioGroup
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.menu_object1 -> camera.enableBoxes()
                R.id.menu_object2 -> camera.disableBoxes()
                R.id.menu_object3 -> Toast.makeText(applicationContext, "Clicked Item 3", Toast.LENGTH_SHORT).show()
                R.id.menu_object4 -> Toast.makeText(applicationContext, "Clicked Item 4", Toast.LENGTH_SHORT).show()
                R.id.menu_object5 -> Toast.makeText(applicationContext, "Clicked Item 5", Toast.LENGTH_SHORT).show()
                R.id.menu_object6 -> Toast.makeText(applicationContext, "Clicked Item 6", Toast.LENGTH_SHORT).show()
                R.id.menu_object7 -> Toast.makeText(applicationContext, "Clicked Item 7", Toast.LENGTH_SHORT).show()
                R.id.menu_object8 -> Toast.makeText(applicationContext, "Clicked Item 8", Toast.LENGTH_SHORT).show()
                R.id.menu_object9 -> Toast.makeText(applicationContext, "Clicked Item 9", Toast.LENGTH_SHORT).show()
                R.id.menu_object10 -> Toast.makeText(applicationContext, "Clicked Item 10", Toast.LENGTH_SHORT).show()
            }
        }


        binding.maxObjectsSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {

            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean)
            {
                customRunOnUIThread { binding.maxObjectsValue.text = seek.progress.toString() }
            }

            override fun onStartTrackingTouch(seek: SeekBar) {}

            override fun onStopTrackingTouch(seek: SeekBar) {}
        })
    }

    companion object {
        private const val TAG = "MobileSensing"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener( {
            val cameraProvider : ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            val rearCamera = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, CameraAnalyzer { image ->
                        camera.attemptDetection(image, binding.maxObjectsSlider.progress, detectionScoreThreshold)
                    })
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this,
                    rearCamera,
                    preview,
                    imageAnalyzer)
            }
            catch(e : Exception) { Log.e(TAG, "Use case binding failed", e) }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun customRunOnUIThread(function : Runnable)
    {
        try {
            runOnUiThread(function)
        }
        catch(e : InterruptedException) {
            e.printStackTrace();
        }
    }

}



private class CameraAnalyzer(private val listener: CameraListener) : ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    override fun analyze(image: ImageProxy) {
        listener(image)

        image.close()
    }
}