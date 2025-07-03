package com.example.weightlossapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
//import androidx.compose.ui.unit.sp // Not directly used in this simplified version
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
//import com.example.weightlossapp.data.model.ActivityLevel // Not directly used here
//import com.example.weightlossapp.data.model.UserProfile // UserProfile is nullable now
import com.example.weightlossapp.ui.theme.WeightLossAppTheme
import com.example.weightlossapp.ui.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val userProfile by profileViewModel.userProfile.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") },
                actions = {
                    IconButton(onClick = { /* TODO: Navigate to Edit Profile Screen */ }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit Profile")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Center content when loading/error
        ) {
            userProfile?.let { profile -> // Only display if profile is not null
                Column(horizontalAlignment = Alignment.Start) { // Align profile info rows to start
                    ProfileInfoRow("Username:", profile.username)
                    ProfileInfoRow("Email:", profile.email)
                    ProfileInfoRow("Age:", profile.age?.toString() ?: "N/A")
                    ProfileInfoRow("Weight:", profile.weightKg?.let { "$it kg" } ?: "N/A")
                    ProfileInfoRow("Height:", profile.heightCm?.let { "$it cm" } ?: "N/A")
                    ProfileInfoRow("Activity Level:", profile.activityLevel?.toString() ?: "N/A")
                    ProfileInfoRow("Daily Calorie Goal:", profile.dailyCalorieGoal?.toString() ?: "N/A")
                    ProfileInfoRow("Dietary Goals:", profile.dietaryGoals ?: "N/A")

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { /* TODO: Implement Logout or other actions */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Logout (Placeholder)")
                    }
                }
            } ?: run {
                // Show a loading indicator or a message if profile is null
                CircularProgressIndicator()
                Text(
                    text = "Loading profile...",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.width(150.dp) // Adjust width as needed
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
    Divider()
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    WeightLossAppTheme {
        // Dummy NavController for preview
        val context = LocalContext.current
        // For preview, we can instantiate ProfileViewModel directly if it has no complex dependencies
        // or use a mock/fake ViewModel.
        ProfileScreen(
            navController = NavController(context),
            profileViewModel = ProfileViewModel() // This uses the actual ViewModel with its mock data
        )
    }
}
