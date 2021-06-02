package com.foodloop.foodloopapps.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import com.foodloop.foodloopapps.data.SharedPreference
import com.foodloop.foodloopapps.ui.menu.MenuLoginActivity
import com.foodloop.foodloopapps.databinding.ActivitySplashScreenBinding
import com.foodloop.foodloopapps.ui.mainactivity.MainActivity

class SplashScreen : AppCompatActivity() {
    private lateinit var splashScreenBinding: ActivitySplashScreenBinding
    private lateinit var sharedPref: SharedPreference
    companion object{
        const val SPLASH_TIME: Long = 4000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = SharedPreference(this)
        splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        WindowManager.LayoutParams.FLAG_FULLSCREEN
        setContentView(splashScreenBinding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MenuLoginActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }
}