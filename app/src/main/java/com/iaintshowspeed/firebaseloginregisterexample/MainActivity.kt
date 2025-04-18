package com.iaintshowspeed.firebaseloginregisterexample

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.iaintshowspeed.firebaseloginregisterexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        
        binding?.btnLogout?.setOnClickListener {
            // Handle logout button click
            logoutUser()
        }
    }
    
    private fun logoutUser() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        // Navigate back to LoginActivity
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}