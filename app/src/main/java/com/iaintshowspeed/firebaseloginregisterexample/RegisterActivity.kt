package com.iaintshowspeed.firebaseloginregisterexample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.iaintshowspeed.firebaseloginregisterexample.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private var binding: ActivityRegisterBinding? = null
    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        
        auth = FirebaseAuth.getInstance()

        binding?.loginLink?.setOnClickListener {
            // Navigate to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        
        binding?.btnRegister?.setOnClickListener {
            // Handle register button click
            registerUser()
        }
    }
    
    private fun registerUser() {
        // Get the email and password from the input fields
        val email = binding?.registerEmail?.text.toString()
        val password = binding?.registerPassword?.text.toString()
        val repeatPassword = binding?.registerRepeatPassword?.text.toString()

        // Validate the input fields
        if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() || password != repeatPassword) {
            Toast.makeText(this, "Please fill in all fields and ensure passwords match", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if the email is a valid Gmail address
        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@(gmail\\.com)$")
        if (!emailRegex.matches(email)) {
            Toast.makeText(this, "Please use a valid Gmail address", Toast.LENGTH_SHORT).show()
            return
        }

        // Disabling the input fields and buttons
        binding?.registerEmail?.isEnabled = false
        binding?.registerPassword?.isEnabled = false
        binding?.registerRepeatPassword?.isEnabled = false
        binding?.btnRegister?.isEnabled = false
        binding?.loginLink?.isEnabled = false
        
        // Attempt to register the user to Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful, navigate to MainActivity
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // Show error message
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    
                    // Re-enable the input fields and buttons
                    binding?.registerEmail?.isEnabled = true
                    binding?.registerPassword?.isEnabled = true
                    binding?.registerRepeatPassword?.isEnabled = true
                    binding?.btnRegister?.isEnabled = true
                    binding?.loginLink?.isEnabled = true
                }
            }
    }
    
}