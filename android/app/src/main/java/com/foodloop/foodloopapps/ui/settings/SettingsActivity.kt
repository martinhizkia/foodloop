package com.foodloop.foodloopapps.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var activitySettingsBinding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(activitySettingsBinding.root)

        supportActionBar?.title = getString(R.string.settings_language)

        changeLanguageSetting()
    }

    private fun changeLanguageSetting() {
        activitySettingsBinding.groupLanguage.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
    }
}