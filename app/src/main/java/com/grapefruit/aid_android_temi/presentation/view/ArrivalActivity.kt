package com.grapefruit.aid_android_temi.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grapefruit.aid_android_temi.R
import com.grapefruit.aid_android_temi.databinding.ActivityArrivalBinding

class ArrivalActivity : AppCompatActivity() {

    lateinit var binding: ActivityArrivalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrival)
    }
}