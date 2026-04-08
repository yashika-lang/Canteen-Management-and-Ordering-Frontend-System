package com.example.smartcanteenapp.ui.auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import kotlinx.coroutines.*
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun RegisterScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF5D4037),
                        Color(0xFF3E2723),
                        Color.Black
                    )
                )
            )
            .padding(24.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                "Create Account",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    scope.launch(Dispatchers.IO) {
                        try {
                            val url = URL("http://192.168.1.40:8080/api/auth/register")
                            val conn = url.openConnection() as HttpURLConnection

                            conn.requestMethod = "POST"
                            conn.doOutput = true
                            conn.setRequestProperty("Content-Type", "application/json")

                            val json = """{"name":"$name","email":"$email","password":"$password"}"""

                            OutputStreamWriter(conn.outputStream).use {
                                it.write(json)
                            }

                            val response = conn.inputStream.bufferedReader().readText()
                            Log.d("REGISTER_RESPONSE", response)

                            withContext(Dispatchers.Main) {
                                message = "Registered Successfully"
                            }

                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                message = "Error registering"
                            }
                        }
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800)
                )
            ) {
                Text("Register", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(message, color = Color.White)
        }
    }
}