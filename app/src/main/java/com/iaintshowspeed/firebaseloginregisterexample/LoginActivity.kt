package com.iaintshowspeed.firebaseloginregisterexample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.iaintshowspeed.firebaseloginregisterexample.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        // Check if user is already logged in
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is signed in, navigate to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        
        auth = FirebaseAuth.getInstance()
        
        binding?.registerLink?.setOnClickListener {
            // Navigate to RegisterActivity
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        
        binding?.btnLogin?.setOnClickListener {
            // Handle login button click
            loginUser()
        }
    }
    
    private fun loginUser() {
        // Get the email and password from the input fields
        val email = binding?.loginEmail?.text.toString()
        val password = binding?.loginPassword?.text.toString()
        
        // Validate the input fields
        if (email.isEmpty() || password.isEmpty()) {
            // Show error message
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Disabling the input fields and buttons
        binding?.loginEmail?.isEnabled = false
        binding?.loginPassword?.isEnabled = false
        binding?.btnLogin?.isEnabled = false
        binding?.registerLink?.isEnabled = false
        
        // Attempt to login the user with Firebase Authentication
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successful, navigate to MainActivity
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    // Re-enable the input fields and buttons
                    binding?.loginEmail?.isEnabled = true
                    binding?.loginPassword?.isEnabled = true
                    binding?.btnLogin?.isEnabled = true
                    binding?.registerLink?.isEnabled = true
                }
            }
    }
}