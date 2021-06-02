package com.foodloop.foodloopapps.ui.changepassword

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.databinding.ActivityChangePasswordBinding
import com.foodloop.foodloopapps.ui.profil.ProfilFragment

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var changePasswordActivity: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changePasswordActivity = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(changePasswordActivity.root)
        supportActionBar?.title=getString(R.string.change_password)
        changePasswordActivity.btnSave.setOnClickListener {
            Toast.makeText(this,getString(R.string.menu_invalid), Toast.LENGTH_SHORT).show()
        }
    }
}