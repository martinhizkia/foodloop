package com.foodloop.foodloopapps.ui.changepassword

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var changePasswordActivity: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changePasswordActivity = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(changePasswordActivity.root)
        supportActionBar?.title = getString(R.string.change_password)
        changePasswordActivity.btnSave.setOnClickListener {
            Toast.makeText(this, getString(R.string.menu_invalid), Toast.LENGTH_SHORT).show()
        }
    }
}