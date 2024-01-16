package com.x3mads.test.chartboostmediationtestapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.chartboost.heliumsdk.HeliumSdk
import com.chartboost.heliumsdk.ad.ChartboostMediationAdLoadRequest
import com.chartboost.heliumsdk.ad.ChartboostMediationAdShowResult
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAd
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAdListener
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAdLoadListener
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAdLoadResult
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAdShowListener
import com.chartboost.heliumsdk.domain.ChartboostMediationAdException
import com.chartboost.heliumsdk.domain.Keywords
import java.lang.ref.WeakReference

class InterstitialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial)
        setSupportActionBar(findViewById(R.id.toolbar))
        title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        findViewById<Button>(R.id.loadAndShowInterstitial).setOnClickListener {
            loadAndShowInterstitial()
        }
    }

    private fun loadAndShowInterstitial() {
        Log.d(TAG, "HeliumSdk: loading interstitial")
        HeliumSdk.loadFullscreenAdFromJava(
            applicationContext,
            ChartboostMediationAdLoadRequest(TODO("Missing placement name"), Keywords()),
            EmptyChartboostMediationFullscreenAdListener(),
            DefaultChartboostMediationFullscreenAdLoadListener(WeakReference(this))
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

class DefaultChartboostMediationFullscreenAdLoadListener(
    private val weakActivity: WeakReference<Activity>
) : ChartboostMediationFullscreenAdLoadListener {
    override fun onAdLoaded(result: ChartboostMediationFullscreenAdLoadResult) {
        Log.d(TAG, "HeliumSdk: interstitial loaded. Showing ad...")
        val activity = weakActivity.get() ?: return
        result.ad?.showFullscreenAdFromJava(activity, EmptyChartboostMediationFullscreenAdShowListener())
    }
}

class EmptyChartboostMediationFullscreenAdShowListener : ChartboostMediationFullscreenAdShowListener {
    override fun onAdShown(result: ChartboostMediationAdShowResult) = Unit
}

class EmptyChartboostMediationFullscreenAdListener : ChartboostMediationFullscreenAdListener {
    override fun onAdClicked(ad: ChartboostMediationFullscreenAd) = Unit
    override fun onAdExpired(ad: ChartboostMediationFullscreenAd) = Unit
    override fun onAdImpressionRecorded(ad: ChartboostMediationFullscreenAd) = Unit
    override fun onAdRewarded(ad: ChartboostMediationFullscreenAd) = Unit
    override fun onAdClosed(
        ad: ChartboostMediationFullscreenAd,
        error: ChartboostMediationAdException?
    ) = Unit
}