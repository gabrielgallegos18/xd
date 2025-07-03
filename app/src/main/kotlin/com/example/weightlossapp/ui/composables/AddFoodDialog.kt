package com.example.weightlossapp.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.weightlossapp.data.model.FoodItem
import com.example.weightlossapp.ui.theme.WeightLossAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (name: String, calories: Int, servingSize: String) -> Unit // Simplified for now
) {
    var foodName by remember { mutableStateOf("") }
    var caloriesText by remember { mutableStateOf("") }
    var servingSize by remember { mutableStateOf("") } // Added serving size

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Add New Food", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = foodName,
                    onValueChange = { foodName = it },
                    label = { Text("Food Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = caloriesText,
                    onValueChange = { caloriesText = it },
                    label = { Text("Calories (kcal)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField( // Added Serving Size Field
                    value = servingSize,
                    onValueChange = { servingSize = it },
                    label = { Text("Serving Size (e.g., 100g, 1 cup)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        val calories = caloriesText.toIntOrNull()
                        if (foodName.isNotBlank() && calories != null && servingSize.isNotBlank()) {
                            onConfirm(foodName, calories, servingSize)
                        }
                        // TODO: Add error handling for invalid input
                    }) {
                        Text("Add")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false) // Dialogs often better previewed without a background
@Composable
fun AddFoodDialogPreview() {
    WeightLossAppTheme {
        // This state is to make the dialog visible in the preview
        var showDialog by remember { mutableStateOf(true) }
        if (showDialog) {
            AddFoodDialog(
                onDismissRequest = { showDialog = false },
                onConfirm = { name, calories, serving ->
                    println("Confirmed: $name, $calories kcal, $serving")
                    showDialog = false
                }
            )
        }
    }
}
