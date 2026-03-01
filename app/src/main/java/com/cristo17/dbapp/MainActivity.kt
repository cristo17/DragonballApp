package com.cristo17.dbapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cristo17.dbapp.ui.navigation.AppNav
import com.cristo17.dbapp.ui.theme.DbappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DbappTheme {
                // Surface proporciona el fondo según el tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // LLAMADA CLAVE: Esto inicia tu lista de 100 guerreros
                    AppNav()
                }
            }
        }
    }
}