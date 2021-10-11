package com.android10_kotlin.myshoppal.ui.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashScreenBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

//        hideStatusBars()

        mBinding.cvSplashScreen.animation =
            AnimationUtils.loadAnimation(this, R.anim.splash_screen_scale_cv_title_anim)

        mBinding.tvSplashScreenTitle.animation =
            AnimationUtils.loadAnimation(this, R.anim.splash_screen_translate_text_anim)

        runWait()
    }

    private fun runWait() {
        coroutineScope.launch {
            wait()
        }
    }

    private suspend fun wait() {
        delay(3000L)
        val intent = Intent(this, LoginActivity::class.java)
//        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun hideStatusBars() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

}