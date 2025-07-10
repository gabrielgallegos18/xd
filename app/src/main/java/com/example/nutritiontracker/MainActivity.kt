package com.example.nutritiontracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    private lateinit var etFoodName: EditText
    private lateinit var etCalories: EditText
    private lateinit var btnAdd: Button
    private lateinit var tvTotal: TextView
    private lateinit var tvFoodList: TextView
    
    private var totalCalories = 0
    private val foodList = mutableListOf<String>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initViews()
        setupClickListeners()
        updateDisplay()
    }
    
    private fun initViews() {
        etFoodName = findViewById(R.id.etFoodName)
        etCalories = findViewById(R.id.etCalories)
        btnAdd = findViewById(R.id.btnAdd)
        tvTotal = findViewById(R.id.tvTotal)
        tvFoodList = findViewById(R.id.tvFoodList)
    }
    
    private fun setupClickListeners() {
        btnAdd.setOnClickListener {
            val foodName = etFoodName.text.toString().trim()
            val caloriesText = etCalories.text.toString().trim()
            
            if (foodName.isNotEmpty() && caloriesText.isNotEmpty()) {
                try {
                    val calories = caloriesText.toInt()
                    addFood(foodName, calories)
                    clearInputs()
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Por favor ingresa un número válido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun addFood(name: String, calories: Int) {
        totalCalories += calories
        foodList.add("$name - $calories cal")
        updateDisplay()
        Toast.makeText(this, "$name agregado", Toast.LENGTH_SHORT).show()
    }
    
    private fun updateDisplay() {
        tvTotal.text = "Total: $totalCalories calorías"
        tvFoodList.text = if (foodList.isEmpty()) {
            "No hay alimentos agregados"
        } else {
            foodList.joinToString("\n")
        }
    }
    
    private fun clearInputs() {
        etFoodName.text.clear()
        etCalories.text.clear()
    }
}