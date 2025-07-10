package com.example.nutritiontracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nutritiontracker.database.FoodEntry
import com.example.nutritiontracker.ui.theme.Green500
import com.example.nutritiontracker.ui.theme.Orange500
import com.example.nutritiontracker.ui.theme.Red500
import com.example.nutritiontracker.ui.viewmodels.NutritionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: NutritionViewModel,
    onAddFoodClick: () -> Unit,
    onLogout: () -> Unit
) {
    val dashboardState by viewModel.dashboardState.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser
    var showGoalDialog by remember { mutableStateOf(false) }
    var newGoalText by remember { mutableStateOf("") }
    
    LaunchedEffect(currentUser) {
        currentUser?.let { username ->
            viewModel.loadDashboardData(username)
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Dashboard") },
            actions = {
                IconButton(onClick = { showGoalDialog = true }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
                IconButton(onClick = onLogout) {
                    Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Green500,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
            )
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Calorie Summary Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Goal Card
                    CalorieCard(
                        title = "Goal",
                        value = dashboardState.goalCalories,
                        color = Green500,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Consumed Card
                    CalorieCard(
                        title = "Consumed",
                        value = dashboardState.consumedCalories,
                        color = Orange500,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Remaining Card
                    CalorieCard(
                        title = "Remaining",
                        value = dashboardState.remainingCalories,
                        color = if (dashboardState.remainingCalories >= 0) Green500 else Red500,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Add Food Button
            item {
                Button(
                    onClick = onAddFoodClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange500)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Food")
                }
            }
            
            // Food Log Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Today's Food Log",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Green500
                        )
                        
                        if (dashboardState.foodEntries.isEmpty()) {
                            Text(
                                text = "No food logged today",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp)
                            )
                        } else {
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
            
            // Food Entries
            items(dashboardState.foodEntries) { entry ->
                FoodEntryItem(
                    entry = entry,
                    onDelete = { viewModel.deleteFoodEntry(entry.id) }
                )
            }
        }
    }
    
    // Goal Setting Dialog
    if (showGoalDialog) {
        AlertDialog(
            onDismissRequest = { showGoalDialog = false },
            title = { Text("Set Daily Calorie Goal") },
            text = {
                OutlinedTextField(
                    value = newGoalText,
                    onValueChange = { newGoalText = it },
                    label = { Text("Calorie Goal") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = { Text(dashboardState.goalCalories.toString()) }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val newGoal = newGoalText.toIntOrNull()
                        if (newGoal != null && newGoal > 0) {
                            currentUser?.let { username ->
                                viewModel.updateCalorieGoal(username, newGoal)
                            }
                            showGoalDialog = false
                            newGoalText = ""
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { 
                    showGoalDialog = false
                    newGoalText = ""
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun CalorieCard(
    title: String,
    value: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Composable
fun FoodEntryItem(
    entry: FoodEntry,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = entry.foodName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Green500
                )
                Text(
                    text = "${entry.servings} servings × ${entry.caloriesPerServing} cal",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = "${entry.totalCalories} cal",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Orange500,
                modifier = Modifier.padding(end = 8.dp)
            )
            
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Red500
                )
            }
        }
    }
}