package com.foodloop.foodloopapps.ui.registration

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodloop.foodloopapps.BuildConfig.BASE_URL
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.data.network.ApiConfig
import com.foodloop.foodloopapps.data.network.ApiService
import com.foodloop.foodloopapps.data.respons.UserRespons
import com.foodloop.foodloopapps.databinding.ActivityRegistrationBinding
import com.foodloop.foodloopapps.ui.login.LoginActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.Function5
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameStream = RxTextView.textChanges(binding.edUsername)
            .skipInitialValue()
            .map { username ->
                username.isEmpty()
            }
        usernameStream.subscribe {
            showUsernameExistAlert(it)
        }

        val fullnameStream = RxTextView.textChanges(binding.edName)
            .skipInitialValue()
            .map { fullname ->
                fullname.isEmpty()
            }
        fullnameStream.subscribe {
            showFullnameExistAlert(it)
        }


        val emailStream = RxTextView.textChanges(binding.edEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailExistAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.edPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 6
            }
        passwordStream.subscribe {
            showPasswordMinimalAlert(it)
        }

        val passwordConfirmationStream = Observable.merge(
            RxTextView.textChanges(binding.edPassword)
                .map { password ->
                    password.toString() != binding.edReenterPassword.text.toString()
                },
            RxTextView.textChanges(binding.edReenterPassword)
                .map { confirmPassword ->
                    confirmPassword.toString() != binding.edPassword.text.toString()
                }
        )
        passwordConfirmationStream.subscribe {
            showPasswordConfirmationAlert(it)
        }

        val invalidFieldsStream = Observable.combineLatest(
            usernameStream,
            fullnameStream,
            emailStream,
            passwordStream,
            passwordConfirmationStream,
            Function5 { usernameInvalid: Boolean, fullnameInvalid: Boolean, emailInvalid: Boolean, passwordInvalid: Boolean, passwordConfirmationInvalid: Boolean ->
                !usernameInvalid && !fullnameInvalid && !emailInvalid && !passwordInvalid && !passwordConfirmationInvalid
            })
        invalidFieldsStream.subscribe { isValid ->
            if (isValid) {
                binding.btnSignup.isEnabled = true
                binding.btnSignup.setBackgroundResource(
                    R.drawable.btn_login_active
                )
                binding.btnSignup.setOnClickListener {
                    creatUserApi()
                }
            } else {
                binding.btnSignup.isEnabled = false
                binding.btnSignup.setBackgroundResource(
                    R.drawable.btn_login
                )
            }
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showUsernameExistAlert(isNotValid: Boolean) {
        binding.edUsername.error = if (isNotValid) getString(R.string.username_not_valid) else null
    }

    private fun showFullnameExistAlert(isNotValid: Boolean) {
        binding.edName.error = if (isNotValid) getString(R.string.name_not_valid) else null
    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.edEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.edPassword.error = if (isNotValid) getString(R.string.password_not_valid) else null
    }

    private fun showPasswordConfirmationAlert(isNotValid: Boolean) {
        binding.edReenterPassword.error =
            if (isNotValid) getString(R.string.password_not_same) else null
    }

    private fun creatUserApi() {
        val username = binding.edUsername.text.toString()
        val password = binding.edReenterPassword.text.toString()
        val fullname = binding.edName.text.toString()
        val email = binding.edEmail.text.toString()

        val user = ApiConfig.getApiService(BASE_URL).create(ApiService::class.java)
        user.createUser(username, password, fullname, email)
            .enqueue(object : Callback<UserRespons> {
                override fun onFailure(call: Call<UserRespons>, t: Throwable) {
                    Log.e("SIGNUP", "Failed: ${t.message.toString()}")
                }

                override fun onResponse(call: Call<UserRespons>, response: Response<UserRespons>) {
                    val user = response.body()
                    user?.status?.let { Log.d("SIGNUP", it) }
                    if (user?.status == "Signup Success") {
                        Toast.makeText(
                            this@RegistrationActivity,
                            user?.status,
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(
                            this@RegistrationActivity,
                            user?.status,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }
}