package com.example.nutritiontracker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritiontracker.database.NutritionDatabase
import com.example.nutritiontracker.repository.NutritionRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {
    
    private lateinit var tvGoalCalories: TextView
    private lateinit var tvConsumedCalories: TextView
    private lateinit var tvRemainingCalories: TextView
    private lateinit var btnAddFood: MaterialButton
    private lateinit var btnLogout: ImageButton
    private lateinit var rvFoodLog: RecyclerView
    private lateinit var tvNoFoodLogged: TextView
    private lateinit var etCalorieGoal: TextInputEditText
    private lateinit var btnSaveGoal: MaterialButton
    
    private lateinit var repository: NutritionRepository
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var foodEntryAdapter: FoodEntryAdapter
    private lateinit var username: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        
        // Get username from intent or SharedPreferences
        username = intent.getStringExtra("username") ?: run {
            sharedPreferences = getSharedPreferences("nutrition_prefs", MODE_PRIVATE)
            sharedPreferences.getString("logged_in_user", null) ?: run {
                // User not logged in, redirect to login
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                return
            }
        }
        
        // Initialize database and repository
        val database = NutritionDatabase.getDatabase(this)
        repository = NutritionRepository(database.userDao(), database.foodDao())
        sharedPreferences = getSharedPreferences("nutrition_prefs", MODE_PRIVATE)
        
        initViews()
        setupRecyclerView()
        setupClickListeners()
        loadUserData()
        observeFoodEntries()
    }
    
    override fun onResume() {
        super.onResume()
        updateCalorieDisplay()
    }
    
    private fun initViews() {
        tvGoalCalories = findViewById(R.id.tvGoalCalories)
        tvConsumedCalories = findViewById(R.id.tvConsumedCalories)
        tvRemainingCalories = findViewById(R.id.tvRemainingCalories)
        btnAddFood = findViewById(R.id.btnAddFood)
        btnLogout = findViewById(R.id.btnLogout)
        rvFoodLog = findViewById(R.id.rvFoodLog)
        tvNoFoodLogged = findViewById(R.id.tvNoFoodLogged)
        etCalorieGoal = findViewById(R.id.etCalorieGoal)
        btnSaveGoal = findViewById(R.id.btnSaveGoal)
    }
    
    private fun setupRecyclerView() {
        foodEntryAdapter = FoodEntryAdapter { foodEntry ->
            deleteFoodEntry(foodEntry.id)
        }
        rvFoodLog.adapter = foodEntryAdapter
        rvFoodLog.layoutManager = LinearLayoutManager(this)
    }
    
    private fun setupClickListeners() {
        btnAddFood.setOnClickListener {
            val intent = Intent(this, AddFoodActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }
        
        btnLogout.setOnClickListener {
            logout()
        }
        
        btnSaveGoal.setOnClickListener {
            val goalText = etCalorieGoal.text.toString().trim()
            if (goalText.isNotEmpty()) {
                try {
                    val goal = goalText.toInt()
                    if (goal > 0) {
                        updateCalorieGoal(goal)
                    } else {
                        Toast.makeText(this, "Please enter a valid calorie goal", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun loadUserData() {
        lifecycleScope.launch {
            try {
                val user = repository.getUser(username)
                user?.let {
                    runOnUiThread {
                        tvGoalCalories.text = it.dailyCalorieGoal.toString()
                        etCalorieGoal.setText(it.dailyCalorieGoal.toString())
                    }
                }
                updateCalorieDisplay()
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@DashboardActivity, "Error loading user data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun observeFoodEntries() {
        lifecycleScope.launch {
            repository.getFoodEntriesForToday(username).collect { foodEntries ->
                runOnUiThread {
                    foodEntryAdapter.submitList(foodEntries)
                    if (foodEntries.isEmpty()) {
                        rvFoodLog.visibility = View.GONE
                        tvNoFoodLogged.visibility = View.VISIBLE
                    } else {
                        rvFoodLog.visibility = View.VISIBLE
                        tvNoFoodLogged.visibility = View.GONE
                    }
                    updateCalorieDisplay()
                }
            }
        }
    }
    
    private fun updateCalorieDisplay() {
        lifecycleScope.launch {
            try {
                val user = repository.getUser(username)
                val totalConsumed = repository.getTotalCaloriesForToday(username)
                
                user?.let {
                    val goalCalories = it.dailyCalorieGoal
                    val remaining = goalCalories - totalConsumed
                    
                    runOnUiThread {
                        tvGoalCalories.text = goalCalories.toString()
                        tvConsumedCalories.text = totalConsumed.toString()
                        tvRemainingCalories.text = remaining.toString()
                        
                        // Change color based on remaining calories
                        if (remaining < 0) {
                            tvRemainingCalories.setTextColor(getColor(R.color.red_500))
                        } else {
                            tvRemainingCalories.setTextColor(getColor(R.color.green_500))
                        }
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@DashboardActivity, "Error updating display: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun updateCalorieGoal(goal: Int) {
        lifecycleScope.launch {
            try {
                repository.updateCalorieGoal(username, goal)
                runOnUiThread {
                    Toast.makeText(this@DashboardActivity, getString(R.string.goal_updated), Toast.LENGTH_SHORT).show()
                    etCalorieGoal.text?.clear()
                    updateCalorieDisplay()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@DashboardActivity, "Error updating goal: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun deleteFoodEntry(id: Long) {
        lifecycleScope.launch {
            try {
                repository.deleteFoodEntry(id)
                runOnUiThread {
                    updateCalorieDisplay()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@DashboardActivity, "Error deleting entry: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun logout() {
        sharedPreferences.edit()
            .remove("logged_in_user")
            .apply()
        
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}