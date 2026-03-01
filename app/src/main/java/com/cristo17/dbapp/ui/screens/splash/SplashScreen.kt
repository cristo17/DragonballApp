package com.cristo17.dbapp.ui.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cristo17.dbapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onAnimationFinished: () -> Unit) {
    // Animación de aparición suave (fade-in)
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1500)
        )
        delay(1000) // Espera un segundo más después de la animación
        onAnimationFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF57C00)), // Color naranja característico
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Usamos el nombre real del archivo que guardaste: imagen.png
            Image(
                painter = painterResource(id = R.drawable.imagen),
                contentDescription = "Esfera del Dragón",
                modifier = Modifier
                    .size(150.dp)
                    .alpha(alpha.value)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "DRAGON BALL CODEX",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                modifier = Modifier.alpha(alpha.value)
            )
            Text(
                text = "por Cristo17",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.alpha(alpha.value)
            )
        }
    }
}
