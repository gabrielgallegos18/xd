package com.example.weightlossapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weightlossapp.navigation.Screen
import com.example.weightlossapp.ui.screens.*
import com.example.weightlossapp.ui.theme.WeightLossAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeightLossAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Login.route) { inclusive = true } // Clear login screen from back stack
                }
            })
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(Screen.CalorieTracker.route) {
            CalorieTrackerScreen(navController)
        }
        composable(Screen.ExerciseLog.route) {
            ExerciseLogScreen(navController)
        }
        composable(Screen.WeightTracker.route) {
            WeightTrackerScreen(navController)
        }
        composable(Screen.Recipe.route) {
            RecipeScreen(navController)
        }
        composable(Screen.WaterTracker.route) {
            WaterTrackerScreen(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeightLossAppTheme {
        // Previewing AppNavigation can be complex if screens have ViewModels.
        // For now, let's try to preview the LoginScreen directly or a simplified AppNavigation.
        // If LoginScreen takes a ViewModel, this preview might need adjustment.
        LoginScreen(onLoginSuccess = {}) // Or a more robust preview of AppNavigation
    }
}
