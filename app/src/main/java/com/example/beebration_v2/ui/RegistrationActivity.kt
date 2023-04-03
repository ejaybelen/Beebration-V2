package com.example.beebration_v2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.beebration_v2.R
import com.example.beebration_v2.databinding.RegisterActivityBinding
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: RegisterActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.createbtn.setOnClickListener {
            val fullNameText = binding.fullame.text.toString()
            val emailText = binding.username2.text.toString()
            val passwordText = binding.newpass.text.toString()
            val confirmPasswordText = binding.confirmpass.text.toString()

            if (fullNameText.isNotEmpty() && emailText.isNotEmpty() && passwordText.isNotEmpty() && confirmPasswordText.isNotEmpty()) {
                if (passwordText == confirmPasswordText) {
                    auth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign up success, go to LoginActivity
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backbtn.setOnClickListener {
            // Go back to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
