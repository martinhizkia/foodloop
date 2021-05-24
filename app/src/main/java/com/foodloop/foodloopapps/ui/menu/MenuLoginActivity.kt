package com.foodloop.foodloopapps.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.foodloop.foodloopapps.ui.login.LoginActivity
import com.foodloop.foodloopapps.databinding.ActivityMenuLoginBinding
import com.foodloop.foodloopapps.ui.registration.RegistrationActivity

class MenuLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}