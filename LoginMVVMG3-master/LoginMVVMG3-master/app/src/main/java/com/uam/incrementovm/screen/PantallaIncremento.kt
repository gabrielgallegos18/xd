package com.uam.incrementovm.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uam.incrementovm.viewmodel.IncrementoViewModel

@Composable
fun IncrementoScreen(viewModel : IncrementoViewModel = viewModel())
{
    var a by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        //Text(viewModel.incremento.toString())
        Text(a.toString())
        Button(onClick = {a++})
            //viewModel.incrementar()})
        {
            Text("Incrementar")
        }
    }

}
