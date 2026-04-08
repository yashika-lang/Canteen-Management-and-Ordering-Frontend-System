package com.example.smartcanteenapp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController

@Composable
fun SplashScreen(navController: NavHostController) {

    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.8f) }

    LaunchedEffect(true) {
        alpha.animateTo(1f, tween(1000))
        scale.animateTo(1f, tween(1000))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF6D4C41), // lighter top
                        Color(0xFF3E2723),
                        Color.Black
                    )
                )
            )
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha.value)
                .scale(scale.value),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            // 🔥 CENTER CONTENT (clean + premium look)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Suswaad",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Canteen",
                    fontSize = 18.sp,
                    color = Color(0xFFFFB74D)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "May the forks be with you",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }

            // 🔥 BUTTON (better styling)
            Button(
                onClick = {
                    navController.navigate("login")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800)
                ),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(
                    "GET STARTED",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}