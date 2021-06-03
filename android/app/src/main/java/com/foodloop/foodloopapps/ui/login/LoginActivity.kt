package com.foodloop.foodloopapps.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodloop.foodloopapps.BuildConfig.BASE_URL
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.SharedPreference
import com.foodloop.foodloopapps.data.network.ApiConfig
import com.foodloop.foodloopapps.data.network.ApiService
import com.foodloop.foodloopapps.data.respons.UserRespons
import com.foodloop.foodloopapps.databinding.ActivityLoginBinding
import com.foodloop.foodloopapps.ui.mainactivity.MainActivity
import com.foodloop.foodloopapps.ui.registration.RegistrationActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: SharedPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = SharedPreference(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnLogin.setOnClickListener {
            val username: String = binding.edUsername.text.toString().trim()
            val password: String = binding.edPassword.text.toString().trim()

            if (username.isEmpty()) {
                binding.edUsername.error = getString(R.string.user_required)
                binding.edUsername.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.edPassword.error = getString(R.string.password_required)
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

        binding.progressBar.visibility = View.VISIBLE

        val user = ApiConfig.getApiService(BASE_URL).create(ApiService::class.java)
        user.postLogin(username, password)
            .enqueue(object : Callback<UserRespons> {
                override fun onFailure(call: Call<UserRespons>, t: Throwable) {
                    Log.e("LOGIN", "Failed: ${t.message.toString()}")
                }

                override fun onResponse(call: Call<UserRespons>, response: Response<UserRespons>) {
                    val user = response.body()
                    binding.progressBar.visibility = View.GONE
                    user?.status?.let { Log.d("LOGIN", it) }
                    if (user?.status == "Wrong Username") {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.user_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (user?.status == "Wrong Password") {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.wrong_pass),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.login_success),
                            Toast.LENGTH_SHORT
                        ).show()
                        user?.email?.let {
                            user.fullname?.let { it1 ->
                                sharedPref.saveUser(
                                    username, it,
                                    it1, true
                                )
                            }
                        }
                        Intent(this@LoginActivity, MainActivity::class.java).also {
                            it.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(it)
                        }
                        finish()
                    }
                }
            })
    }
}