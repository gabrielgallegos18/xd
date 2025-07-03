package com.uam.incrementovm.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uam.incrementovm.model.User
import com.uam.incrementovm.network.ServiceLocator
import com.uam.incrementovm.viewmodel.UserDetailViewModel
import com.uam.incrementovm.viewmodel.UserDetailViewModelFactory
import com.uam.incrementovm.viewmodel.UserViewModel
import com.uam.incrementovm.viewmodel.UserViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailUser(id:Int){
    val context = LocalContext.current
    val repository = remember {ServiceLocator.provideUserRepository(context)}
    val factory = remember { UserDetailViewModelFactory(repository) }

    val viewModel : UserDetailViewModel = viewModel(factory = factory)

    var user by remember { mutableStateOf<User?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(id) {
        try {
            user = viewModel.getUserById(id)
        } catch (e: Exception) {
            error = e.message
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detalle de Usuario") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues))
        {
           /* Image(
                painter = painterResource(id = android.R.drawable),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )*/
           Formulario(viewModel,user,"C")
        }
    }
}
@Composable
fun Formulario(viewModel:UserDetailViewModel,user:User?,accion : String) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var apellido by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    when(accion) {
        "C"->{
            user?.let {
                Text(it.nombre)
                Text(it.apellido)
                Text(it.email)
            } ?: Text("Error con los datos")
        }
        "I"->{
            TextField(value=nombre,
                onValueChange = {nombre = it},
                label= {Text("Nombre")}
                )
            TextField(value=apellido,
                onValueChange = {apellido = it},
                label= {Text("Apellido")}
            )
            TextField(value=correo,
                onValueChange = {correo = it},
                label= {Text("Correo")}
            )
        }
    }
}