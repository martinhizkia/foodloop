package com.foodloop.foodloopapps.ui.changepassword

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.databinding.ActivityChangePasswordBinding
import com.foodloop.foodloopapps.ui.profil.ProfilFragment

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var changePasswordActivity: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changePasswordActivity = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(changePasswordActivity.root)
        supportActionBar?.title="Change Password"
        changePasswordActivity.btnSave.setOnClickListener {
            val mIntent = Intent(this, ProfilFragment::class.java)
            startActivity(mIntent)
        }
    }
}