package com.example.smartcanteenapp.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartcanteenapp.navigation.Routes

@Composable
fun SuccessScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6D4C41),
                        Color(0xFF3E2723)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD89C5B)),
            modifier = Modifier.padding(24.dp)
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "✅",
                    fontSize = 48.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "You have successfully placed your order",
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate(Routes.USER_ORDERS) {
                            popUpTo("home") { inclusive = false }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB87333)
                    ),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text("VIEW ORDER", color = Color.White)
                }
            }
        }
    }
}