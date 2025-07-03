package com.uam.incrementovm.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uam.incrementovm.model.AuthResult
import com.uam.incrementovm.viewmodel.AuthViewModel

@Composable
fun LoginScreen(onNext:()->Unit)
{
    val viewModel : AuthViewModel = viewModel()
    val state by viewModel.loginState.collectAsState()
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        TextField(
            value = username,
            onValueChange = {username = it},
            label = { Text("Usuario") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = password,
            onValueChange = {password = it},
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {viewModel.login(username,password)}
        ) {
            Text("Conectarse")
        }
        Spacer(modifier = Modifier.height(10.dp))
        when(val s = state) {
            is AuthResult.Idle -> Text("Ingrese tus credenciales")
            is AuthResult.Loading -> CircularProgressIndicator()
            is AuthResult.Success -> {
                Text("Bienvenido/a ${s.user.nombre}")
                onNext()
            }
            is AuthResult.Error -> Text("Error: ${s.message}", color = Color.Red)
        }

    }
}