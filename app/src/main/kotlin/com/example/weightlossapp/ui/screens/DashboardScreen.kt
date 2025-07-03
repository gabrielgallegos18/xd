package com.example.weightlossapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Dashboard Screen")
        // Add buttons to navigate to other feature screens later
    }
}

// Preview for DashboardScreen might require a NavController,
// which can be mocked or omitted for simple placeholders.
@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    // For preview, we can pass a dummy NavController or adapt the screen
    // For now, let's assume no NavController interaction in the preview
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Dashboard Screen")
    }
}
