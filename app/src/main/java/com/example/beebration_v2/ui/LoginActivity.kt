package com.example.beebration_v2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.beebration_v2.MainActivity
import com.example.beebration_v2.R
import com.example.beebration_v2.databinding.LoginActivityBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginbtn.setOnClickListener {
            val emailText = binding.username.text.toString()
            val passwordText = binding.password.text.toString()

            if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                auth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, go to MainActivity
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in both fields.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerbtn.setOnClickListener {
            // Go to RegistrationActivity
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }
}
