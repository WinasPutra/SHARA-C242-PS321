package com.example.shara.ui.auth.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shara.databinding.ActivityRegisterBinding
import com.example.shara.ui.auth.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRegister()
    }

    private fun setupRegister(){
        binding.buttonForRegister.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}