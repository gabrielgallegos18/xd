package com.uam.incrementovm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uam.incrementovm.screen.IncrementoScreen
import com.uam.incrementovm.screen.ListUserScreen
import com.uam.incrementovm.screen.LoginScreen
import com.uam.incrementovm.ui.theme.IncrementoVMTheme
import com.uam.navegacionobject.navigacion.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IncrementoVMTheme {
                Surface {
                    Navigation()
                }
            }
        }
    }
}

