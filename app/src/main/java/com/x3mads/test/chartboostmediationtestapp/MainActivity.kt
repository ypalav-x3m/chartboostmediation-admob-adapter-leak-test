package com.x3mads.test.chartboostmediationtestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.chartboost.heliumsdk.HeliumSdk

const val TAG = "AdMobLeakTest"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.initializeHelium).setOnClickListener {
            initializeHeliumSdk()
        }
        findViewById<Button>(R.id.buttonInterstitialActivity).setOnClickListener {
            startActivity(Intent(this, InterstitialActivity::class.java))
        }
    }

    private fun initializeHeliumSdk() {
        Log.d(TAG, "HeliumSdk: starting SDK")
        HeliumSdk.start(
            this,
            TODO("Missing app id"),
            TODO("Missing app signature"),
            heliumSdkListener = object : HeliumSdk.HeliumSdkListener {
                override fun didInitialize(error: Error?) {
                    if (error != null)
                        Log.e(TAG, "HeliumSdk: initialization error: $error")
                    else
                        Log.d(TAG, "HeliumSdk: initialized successfully")
                }
            })
    }
}