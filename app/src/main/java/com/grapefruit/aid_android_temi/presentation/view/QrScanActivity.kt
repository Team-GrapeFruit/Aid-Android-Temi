package com.grapefruit.aid_android_temi.presentation.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.ActivityQrScanBinding
import com.grapefruit.aid_android_temi.presentation.viewmodel.MainViewModel
import com.robotemi.sdk.Robot
import java.io.IOException

class QrScanActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private lateinit var cameraSource: CameraSource
    private lateinit var surfaceView: SurfaceView
    private var isScanned: Boolean = false
    lateinit var binding: ActivityQrScanBinding
    private val viewModel: MainViewModel by viewModels()
    private val robot = Robot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_qr_scan)

        surfaceView = binding.surfaceView
        surfaceView.holder.addCallback(this)

        robot.getInstance().setKioskModeOn(true)
        robot.getInstance().hideTopBar()

        checkPermission()

        viewModel.storeInfo.observe(this) {
            binding.qrBtn.visibility = VISIBLE
            binding.qrBtn.setOnClickListener {
                val intent = Intent(this, SeatReserveActivity::class.java)
                intent.putExtra("storeId", viewModel.storeInfo.value?.storeId)
                startActivity(intent)
            }
        }
    }

    private fun checkPermission() {
        val cameraPermission =
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
            // 카메라 권한이 승인된 상태일 경우
            setupControls()
        } else {
            // 카메라 권한이 승인되지 않았을 경우
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1004)
    }

    private fun setupControls() {
        val barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setAutoFocusEnabled(true)
            .build()

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                cameraSource.stop()
                isScanned = false
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                if (true) {
                    isScanned = true
                    val barcodes = detections.detectedItems
                    if (barcodes.size() != 0) {
                        print("test")
                        val barcodeValue = barcodes.valueAt(0).displayValue // 스캔된 바코드 값
                        viewModel.storeLoad(barcodeValue.toLong())
                        release()
                        finish()
                    }
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1004 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupControls()
                } else {
                    finish()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        //checkPermission()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}
}