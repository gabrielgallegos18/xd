package com.example.nutritiontracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nutritiontracker.ui.theme.Green500
import com.example.nutritiontracker.ui.theme.Orange500
import com.example.nutritiontracker.ui.viewmodels.NutritionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodScreen(
    viewModel: NutritionViewModel,
    onFoodAdded: () -> Unit,
    onCancel: () -> Unit
) {
    var foodName by remember { mutableStateOf("") }
    var caloriesPerServing by remember { mutableStateOf("") }
    var servings by remember { mutableStateOf("1.0") }
    var showError by remember { mutableStateOf(false) }
    
    val currentUser by viewModel.currentUser
    val isLoading by viewModel.isLoading
    
    // Calculate total calories in real time
    val totalCalories = remember(caloriesPerServing, servings) {
        val calories = caloriesPerServing.toIntOrNull() ?: 0
        val servingCount = servings.toDoubleOrNull() ?: 0.0
        (calories * servingCount).toInt()
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Add Food") },
            navigationIcon = {
                IconButton(onClick = onCancel) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Green500,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Main Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Add New Food Entry",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Green500,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    // Food Name Field
                    OutlinedTextField(
                        value = foodName,
                        onValueChange = { 
                            foodName = it
                            showError = false
                        },
                        label = { Text("Food Name") },
                        placeholder = { Text("e.g., Apple, Chicken Breast") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = showError && foodName.isBlank()
                    )
                    
                    // Calories Per Serving Field
                    OutlinedTextField(
                        value = caloriesPerServing,
                        onValueChange = { 
                            caloriesPerServing = it
                            showError = false
                        },
                        label = { Text("Calories per Serving") },
                        placeholder = { Text("e.g., 95") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = showError && caloriesPerServing.toIntOrNull() == null
                    )
                    
                    // Number of Servings Field
                    OutlinedTextField(
                        value = servings,
                        onValueChange = { 
                            servings = it
                            showError = false
                        },
                        label = { Text("Number of Servings") },
                        placeholder = { Text("e.g., 1.5") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        isError = showError && servings.toDoubleOrNull() == null
                    )
                    
                    // Total Calories Display
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Total Calories",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = totalCalories.toString(),
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = Orange500
                            )
                        }
                    }
                    
                    // Error Message
                    if (showError) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Text(
                                text = "Please fill all fields with valid values",
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    
                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Cancel Button
                        OutlinedButton(
                            onClick = onCancel,
                            modifier = Modifier.weight(1f),
                            enabled = !isLoading
                        ) {
                            Text("Cancel")
                        }
                        
                        // Save Button
                        Button(
                            onClick = {
                                val calories = caloriesPerServing.toIntOrNull()
                                val servingCount = servings.toDoubleOrNull()
                                
                                if (foodName.isNotBlank() && calories != null && calories > 0 && 
                                    servingCount != null && servingCount > 0) {
                                    currentUser?.let { username ->
                                        viewModel.addFoodEntry(
                                            username = username,
                                            foodName = foodName,
                                            caloriesPerServing = calories,
                                            servings = servingCount
                                        ) { success ->
                                            if (success) {
                                                onFoodAdded()
                                            }
                                        }
                                    }
                                } else {
                                    showError = true
                                }
                            },
                            modifier = Modifier.weight(1f),
                            enabled = !isLoading
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text("Save Food")
                            }
                        }
                    }
                }
            }
            
            // Tips Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "💡 Tips:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Look for calorie information on food packaging\n• Use decimal numbers for partial servings (e.g., 0.5)\n• Be as accurate as possible for better tracking",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}