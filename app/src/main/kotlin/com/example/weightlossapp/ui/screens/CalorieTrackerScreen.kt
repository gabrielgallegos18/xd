package com.example.weightlossapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp // Not directly used in this simplified example
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weightlossapp.data.model.FoodItem
import com.example.weightlossapp.data.model.MealType
import com.example.weightlossapp.ui.composables.AddFoodDialog
import com.example.weightlossapp.ui.theme.WeightLossAppTheme
import com.example.weightlossapp.ui.viewmodels.CalorieTrackerViewModel

// Mock data for previewing - can be removed if ViewModel provides sufficient preview data
// val mockFoodItems = listOf(
//    FoodItem(name = "Apple", calories = 95, servingSize = "1 medium", mealType = MealType.SNACK),
//    FoodItem(name = "Chicken Breast", calories = 165, servingSize = "100g", mealType = MealType.LUNCH),
//    FoodItem(name = "Salad", calories = 150, servingSize = "1 bowl", mealType = MealType.LUNCH)
// )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalorieTrackerScreen(
    navController: NavController,
    calorieTrackerViewModel: CalorieTrackerViewModel = viewModel()
) {
    val uiState by calorieTrackerViewModel.uiState.collectAsState()

    if (uiState.showAddFoodDialog) {
        AddFoodDialog(
            onDismissRequest = { calorieTrackerViewModel.dismissAddFoodDialog() },
            onConfirm = { name, calories, servingSize ->
                // Potentially add mealType selection in dialog later
                calorieTrackerViewModel.addFoodItem(name, calories, servingSize)
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Daily Calorie Intake") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { calorieTrackerViewModel.showAddFoodDialog() }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Food")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Summary Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Today's Total:", style = MaterialTheme.typography.headlineSmall)
                Text(
                    "${uiState.totalCaloriesToday} kcal",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            // Food List Section
            if (uiState.foodItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No food items logged yet for today.")
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.foodItems) { foodItem ->
                        FoodListItem(foodItem = foodItem)
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun FoodListItem(foodItem: FoodItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(foodItem.name, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold))
            Text(
                "${foodItem.servingSize} - ${foodItem.mealType?.toString() ?: "Unspecified"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text("${foodItem.calories} kcal", style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun CalorieTrackerScreenPreview() {
    WeightLossAppTheme {
        val context = LocalContext.current
        // Preview with a CalorieTrackerViewModel instance that might have mock data
        CalorieTrackerScreen(
            navController = NavController(context),
            calorieTrackerViewModel = CalorieTrackerViewModel() // ViewModel will init with its own (mock) data if any
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FoodListItemPreview() {
    WeightLossAppTheme {
        FoodListItem(foodItem = FoodItem(name = "Banana", calories = 105, servingSize = "1 large", mealType = MealType.BREAKFAST))
    }
}
