package com.example.smartcanteenapp.ui.auth

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun LoginScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF3E2723),
                        Color(0xFF2D1B17),
                        Color.Black
                    )
                )
            )
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Welcome Back",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Login to continue",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFFFF9800),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFFFF9800),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    scope.launch(Dispatchers.IO) {
                        try {
                            val url = URL("http://192.168.1.40:8080/api/auth/login")
                            val conn = url.openConnection() as HttpURLConnection

                            conn.requestMethod = "POST"
                            conn.doOutput = true
                            conn.setRequestProperty("Content-Type", "application/json")

                            val json = """{"email":"$email","password":"$password"}"""

                            OutputStreamWriter(conn.outputStream).use {
                                it.write(json)
                            }

                            val response = conn.inputStream.bufferedReader().readText()
                            Log.d("LOGIN_RESPONSE", response)

                            val jsonObj = JSONObject(response)
                            val name = jsonObj.optString("name", "User")
                            val role = jsonObj.optString("role", "USER")

                            sharedPref.edit().putString("name", name).apply()

                            withContext(Dispatchers.Main) {

                                if (role == "ADMIN") {
                                    navController.navigate("admin")
                                } else {
                                    navController.navigate("home")
                                }
                            }

                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                error = "Login failed. Server ya network check karo"
                            }
                        }
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800)
                )
            ) {
                Text("Login", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (error.isNotEmpty()) {
                Text(error, color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("New user? ", color = Color.Gray)
                Text(
                    "Register",
                    color = Color(0xFFFF9800),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        navController.navigate("register")
                    }
                )
            }
        }
    }
}
