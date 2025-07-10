package com.example.nutritiontracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nutritiontracker.ui.screens.AddFoodScreen
import com.example.nutritiontracker.ui.screens.DashboardScreen
import com.example.nutritiontracker.ui.screens.LoginScreen
import com.example.nutritiontracker.ui.viewmodels.NutritionViewModel
import com.example.nutritiontracker.ui.viewmodels.NutritionViewModelFactory

@Composable
fun NutritionNavigation(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: NutritionViewModel = viewModel(
        factory = NutritionViewModelFactory(context)
    )
    
    var startDestination by remember { mutableStateOf("login") }
    
    // Check if user is already logged in
    LaunchedEffect(Unit) {
        val loggedInUser = viewModel.getLoggedInUser()
        if (loggedInUser != null) {
            startDestination = "dashboard"
        }
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        
        composable("dashboard") {
            DashboardScreen(
                viewModel = viewModel,
                onAddFoodClick = {
                    navController.navigate("add_food")
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                }
            )
        }
        
        composable("add_food") {
            AddFoodScreen(
                viewModel = viewModel,
                onFoodAdded = {
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
    }
}