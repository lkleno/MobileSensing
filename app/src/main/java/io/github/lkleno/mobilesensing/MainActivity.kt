package io.github.lkleno.mobilesensing

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
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
import io.github.lkleno.mobilesensing.layout.CustomDrawerLayout
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


typealias LumaListener = (luma : Double) -> Unit
private lateinit var binding : ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var imageCapture : ImageCapture? = null
    private var videoCapture : VideoCapture<Recorder>? = null
    private var recording : Recording? = null

    private lateinit var menuToggle : ActionBarDrawerToggle
    private lateinit var cameraExecutor : ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        flagSetup()

        // Check Camera Permissions
        if(allPermissionsGranted()) startCamera()
        else ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)

        uiSetup(view)

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

    private fun uiSetup(view : CustomDrawerLayout)
    {
        menuToggle = ActionBarDrawerToggle(this, view, R.string.menu_open, R.string.menu_close)
        view.addDrawerListener(menuToggle)
        menuToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.maxObjectsValue.text = binding.maxObjectsSlider.progress.toString()


        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun listenerSetup()
    {
        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menu_object1 -> Toast.makeText(applicationContext, "Clicked Item 1", Toast.LENGTH_SHORT).show()
                R.id.menu_object2 -> Toast.makeText(applicationContext, "Clicked Item 2", Toast.LENGTH_SHORT).show()
                R.id.menu_object3 -> Toast.makeText(applicationContext, "Clicked Item 3", Toast.LENGTH_SHORT).show()
                R.id.menu_object4 -> Toast.makeText(applicationContext, "Clicked Item 4", Toast.LENGTH_SHORT).show()
                R.id.menu_object5 -> Toast.makeText(applicationContext, "Clicked Item 5", Toast.LENGTH_SHORT).show()
            }
            true
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
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                        customRunOnUIThread { binding.lumaValue.text = String.format("%.2f", luma) }
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

// FIXME TEMP TO TEST OUT IMAGE

private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    override fun analyze(image: ImageProxy) {

        val buffer = image.planes[0].buffer
        val data = buffer.toByteArray()
        val pixels = data.map { it.toInt() and 0xFF }
        val luma = pixels.average()

        listener(luma)

        image.close()
    }
}