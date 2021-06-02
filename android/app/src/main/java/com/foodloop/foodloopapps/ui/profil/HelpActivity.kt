package com.foodloop.foodloopapps.ui.profil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {
    private lateinit var helpBinding: ActivityHelpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        helpBinding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(helpBinding.root)

        supportActionBar?.title = getString(R.string.help)
    }
}