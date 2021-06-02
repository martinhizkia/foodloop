package com.foodloop.foodloopapps.ui.profil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var aboutBinding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aboutBinding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(aboutBinding.root)

        supportActionBar?.title = getString(R.string.about_us)
    }
}