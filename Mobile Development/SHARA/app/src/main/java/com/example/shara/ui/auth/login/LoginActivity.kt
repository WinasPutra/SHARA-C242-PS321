package com.example.shara.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.shara.data.Result
import com.example.shara.data.ViewModelFactory
import com.example.shara.databinding.ActivityLoginBinding
import com.example.shara.ui.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userLogin()
        showLoading(false)
        playAnimation()
    }
    private fun userLogin(){
        binding.buttonForLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                loginViewModel.userLogin(email, password).observe(this){result ->
                    if (result!=null){
                        when(result){
                            is Result.Loading -> showLoading(true)

                            is Result.Success ->{
                                showLoading(false)
                                AlertDialog.Builder(this).apply {
                                    setTitle("Oh Yeah!")
                                    setMessage("Berhasil Login!!")
                                    setPositiveButton("Next") { _, _ ->
                                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }

                            is Result.Error -> {
                                showLoading(false)
                                Toast.makeText(this, "Login Gagal: ${result.error}", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }
            }
        }
    }

    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val email = ObjectAnimator.ofFloat(binding.textInputLoginLayoutEmail, View.ALPHA, 1f).setDuration(200)
        val password = ObjectAnimator.ofFloat(binding.textInputLoginLayoutPassword, View.ALPHA, 1f).setDuration(200)
        val login = ObjectAnimator.ofFloat(binding.buttonForLogin, View.ALPHA, 1f).setDuration(200)

        AnimatorSet().apply {
            playSequentially(
                email,
                password,
                login)
            startDelay = 100
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}