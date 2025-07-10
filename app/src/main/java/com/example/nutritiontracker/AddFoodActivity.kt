package com.example.nutritiontracker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.nutritiontracker.database.NutritionDatabase
import com.example.nutritiontracker.repository.NutritionRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class AddFoodActivity : AppCompatActivity() {
    
    private lateinit var etFoodName: TextInputEditText
    private lateinit var etCaloriesPerServing: TextInputEditText
    private lateinit var etServings: TextInputEditText
    private lateinit var tvTotalCalories: TextView
    private lateinit var btnSave: MaterialButton
    private lateinit var btnCancel: MaterialButton
    
    private lateinit var repository: NutritionRepository
    private lateinit var username: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)
        
        // Get username from intent
        username = intent.getStringExtra("username") ?: run {
            finish()
            return
        }
        
        // Initialize database and repository
        val database = NutritionDatabase.getDatabase(this)
        repository = NutritionRepository(database.userDao(), database.foodDao())
        
        initViews()
        setupClickListeners()
        setupTextWatchers()
    }
    
    private fun initViews() {
        etFoodName = findViewById(R.id.etFoodName)
        etCaloriesPerServing = findViewById(R.id.etCaloriesPerServing)
        etServings = findViewById(R.id.etServings)
        tvTotalCalories = findViewById(R.id.tvTotalCalories)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)
        
        // Set default serving to 1
        etServings.setText("1.0")
    }
    
    private fun setupClickListeners() {
        btnSave.setOnClickListener {
            saveFoodEntry()
        }
        
        btnCancel.setOnClickListener {
            finish()
        }
    }
    
    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateTotalCalories()
            }
        }
        
        etCaloriesPerServing.addTextChangedListener(textWatcher)
        etServings.addTextChangedListener(textWatcher)
    }
    
    private fun calculateTotalCalories() {
        try {
            val caloriesPerServing = etCaloriesPerServing.text.toString().toIntOrNull() ?: 0
            val servings = etServings.text.toString().toDoubleOrNull() ?: 0.0
            val totalCalories = (caloriesPerServing * servings).toInt()
            
            tvTotalCalories.text = "Total Calories: $totalCalories"
        } catch (e: Exception) {
            tvTotalCalories.text = "Total Calories: 0"
        }
    }
    
    private fun saveFoodEntry() {
        val foodName = etFoodName.text.toString().trim()
        val caloriesPerServingText = etCaloriesPerServing.text.toString().trim()
        val servingsText = etServings.text.toString().trim()
        
        if (foodName.isEmpty() || caloriesPerServingText.isEmpty() || servingsText.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            return
        }
        
        try {
            val caloriesPerServing = caloriesPerServingText.toInt()
            val servings = servingsText.toDouble()
            
            if (caloriesPerServing <= 0 || servings <= 0) {
                Toast.makeText(this, "Please enter valid positive numbers", Toast.LENGTH_SHORT).show()
                return
            }
            
            lifecycleScope.launch {
                try {
                    repository.addFoodEntry(username, foodName, caloriesPerServing, servings)
                    runOnUiThread {
                        Toast.makeText(this@AddFoodActivity, getString(R.string.food_added), Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@AddFoodActivity, "Error saving food entry: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
        }
    }
}