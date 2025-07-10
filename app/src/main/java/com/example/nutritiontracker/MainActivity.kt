package com.example.nutritiontracker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.nutritiontracker.database.NutritionDatabase
import com.example.nutritiontracker.repository.NutritionRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnRegister: MaterialButton
    
    private lateinit var repository: NutritionRepository
    private lateinit var sharedPreferences: SharedPreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize database and repository
        val database = NutritionDatabase.getDatabase(this)
        repository = NutritionRepository(database.userDao(), database.foodDao())
        
        // Initialize SharedPreferences for storing login state
        sharedPreferences = getSharedPreferences("nutrition_prefs", MODE_PRIVATE)
        
        // Check if user is already logged in
        val savedUsername = sharedPreferences.getString("logged_in_user", null)
        if (savedUsername != null) {
            navigateToDashboard(savedUsername)
            return
        }
        
        initViews()
        setupClickListeners()
    }
    
    private fun initViews() {
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
    }
    
    private fun setupClickListeners() {
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            
            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser(username, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
        
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            
            if (username.isNotEmpty() && password.isNotEmpty()) {
                registerUser(username, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun loginUser(username: String, password: String) {
        lifecycleScope.launch {
            try {
                val user = repository.loginUser(username, password)
                if (user != null) {
                    // Save login state
                    sharedPreferences.edit()
                        .putString("logged_in_user", username)
                        .apply()
                    
                    navigateToDashboard(username)
                } else {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun registerUser(username: String, password: String) {
        lifecycleScope.launch {
            try {
                val success = repository.registerUser(username, password)
                runOnUiThread {
                    if (success) {
                        Toast.makeText(this@MainActivity, getString(R.string.registration_success), Toast.LENGTH_SHORT).show()
                        etPassword.text?.clear()
                    } else {
                        Toast.makeText(this@MainActivity, getString(R.string.username_exists), Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun navigateToDashboard(username: String) {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
        finish()
    }
}