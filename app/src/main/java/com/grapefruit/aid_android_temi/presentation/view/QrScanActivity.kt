package com.grapefruit.aid_android_temi.presentation.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.ActivityQrScanBinding
import com.grapefruit.aid_android_temi.presentation.viewmodel.MainViewModel
import com.robotemi.sdk.Robot


class QrScanActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var binding: ActivityQrScanBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var robot: Robot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qr_scan)

        setupPermissions()
        setupCodeScanner()

        robot = Robot.getInstance()
        robot.setKioskModeOn(true)
        robot.hideTopBar()

        viewModel.storeInfo.observe(this) {
            binding.qrBtn.visibility = VISIBLE
            binding.qrBtn.setOnClickListener {
                val intent = Intent(this, SeatReserveActivity::class.java)
                intent.putExtra("storeId", viewModel.storeInfo.value?.storeId)
                startActivity(intent)
            }
        }
    }

    private fun setupPermissions() {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1004)
        }
    }

    private fun setupCodeScanner() {
        val codeScannerView = binding.codeScannerView
        codeScanner = CodeScanner(this, codeScannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    val barcodeValue = it.text
                    viewModel.storeLoad(barcodeValue.toLong())
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("QR", it.message!!)
                }
            }

            codeScannerView.setOnClickListener {
                codeScanner.startPreview()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1004 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupCodeScanner()
                } else {
                    finish()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }
}
