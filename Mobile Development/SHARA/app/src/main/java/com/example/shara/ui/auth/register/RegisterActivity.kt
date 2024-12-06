package com.example.shara.ui.auth.register

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
import com.example.shara.databinding.ActivityRegisterBinding
import com.example.shara.ui.auth.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRegister()
        showLoading(false)
        playAnimation()
    }

    private fun setupRegister() {
        binding.buttonForRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                registerViewModel.userReg(name, email, password).observe(this){ result ->
                    if (result!=null){
                        when(result){
                            is Result.Loading -> showLoading(true)

                            is Result.Success ->{
                                showLoading(false)
                                AlertDialog.Builder(this).apply {
                                    setTitle("Yeah")
                                    setMessage("Kamu berhasil daftar akun, lanjutkan login!!")
                                    setPositiveButton("Next!"){_, _ ->
                                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }
                            is Result.Error ->{
                                showLoading(false)
                                Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
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

        val name =
            ObjectAnimator.ofFloat(binding.textInputLayoutUsername, View.ALPHA, 1f).setDuration(200)
        val email =
            ObjectAnimator.ofFloat(binding.textInputLayoutEmail, View.ALPHA, 1f).setDuration(200)
        val password =
            ObjectAnimator.ofFloat(binding.textInputLayoutPassword, View.ALPHA, 1f).setDuration(200)
        val register =
            ObjectAnimator.ofFloat(binding.buttonForRegister, View.ALPHA, 1f).setDuration(200)

        AnimatorSet().apply {
            playSequentially(
                name,
                email,
                password,
                register)
            startDelay = 100
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbRegister.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}