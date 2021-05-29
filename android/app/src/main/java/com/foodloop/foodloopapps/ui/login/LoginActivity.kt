package com.foodloop.foodloopapps.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodloop.foodloopapps.BuildConfig
import com.foodloop.foodloopapps.BuildConfig.BASE_URL
import com.foodloop.foodloopapps.data.network.ApiConfig
import com.foodloop.foodloopapps.data.network.ApiService
import com.foodloop.foodloopapps.data.respons.UserRespons
import com.foodloop.foodloopapps.ui.mainactivity.MainActivity
import com.foodloop.foodloopapps.databinding.ActivityLoginBinding
import com.foodloop.foodloopapps.ui.registration.RegistrationActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences= getSharedPreferences("SHARE LOGIN", Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            val username : String = binding.edUsername.text.toString().trim()
            val password : String = binding.edPassword.text.toString().trim()

            if (username.isEmpty()){
                binding.edUsername.error = "Username is required"
                binding.edUsername.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.edPassword.error = "Password is required"
                binding.edPassword.requestFocus()
                return@setOnClickListener
            }
            loginUserApi()
        }

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUserApi() {
        val username = binding.edUsername.text.toString()
        val password = binding.edPassword.text.toString()

        val user = ApiConfig.getApiService(BASE_URL).create(ApiService::class.java)
        user.postLogin(username, password)
            .enqueue(object : Callback<UserRespons> {
                override fun onFailure(call: Call<UserRespons>, t: Throwable) {
                    Log.e("LOGIN", "Failed: ${t.message.toString()}")
                }

                override fun onResponse(call: Call<UserRespons>, response: Response<UserRespons>) {
                    val user = response.body()
                    user?.status?.let { Log.d("LOGIN", it) }
                    if (user?.status == "Login Success") {
                        Toast.makeText(
                            this@LoginActivity,
                            user?.status,
                            Toast.LENGTH_SHORT
                        ).show()
                        val login: SharedPreferences.Editor = sharedPreferences.edit()
                        login.putString("USERNAME", username)
                        login.putString("NAME", user.fullname)
                        login.putString("EMAIL", user.email)
                        login.apply()
                        Intent(this@LoginActivity, MainActivity::class.java).also {
                            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(it)
                        }
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            user?.status,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }
}