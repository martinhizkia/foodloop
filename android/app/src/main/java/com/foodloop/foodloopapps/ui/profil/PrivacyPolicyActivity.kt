package com.foodloop.foodloopapps.ui.profil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicyActivity : AppCompatActivity() {
    private lateinit var privacyPolicyActivity: ActivityPrivacyPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        privacyPolicyActivity = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(privacyPolicyActivity.root)

        supportActionBar?.title = getString(R.string.public_policy)
    }
}