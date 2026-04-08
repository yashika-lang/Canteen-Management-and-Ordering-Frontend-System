package com.example.smartcanteenapp.ui.admin
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartcanteenapp.viewmodel.AdminViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun AddItemScreen() {

    val viewModel: AdminViewModel = viewModel()
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF6D4C41))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.Center)
        ) {

            Text(
                text = "Add Item",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(modifier = Modifier.padding(20.dp)) {

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("Item Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF9800),
                            unfocusedBorderColor = Color.DarkGray,
                            cursorColor = Color(0xFFFF9800)
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        placeholder = { Text("Price") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF9800),
                            unfocusedBorderColor = Color.DarkGray,
                            cursorColor = Color(0xFFFF9800)
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF9800),
                            unfocusedBorderColor = Color.DarkGray,
                            cursorColor = Color(0xFFFF9800)
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = imageUrl,
                        onValueChange = { imageUrl = it },
                        placeholder = { Text("Image URL") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF9800),
                            unfocusedBorderColor = Color.DarkGray,
                            cursorColor = Color(0xFFFF9800)
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        placeholder = { Text("Category") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFFF9800),
                            unfocusedBorderColor = Color.DarkGray,
                            cursorColor = Color(0xFFFF9800)
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (name.isBlank() || price.isBlank()) {
                                Toast.makeText(context, "Please fill required fields 😅", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            viewModel.addItem(
                                name,
                                price.toDoubleOrNull() ?: 0.0,
                                category,
                                description,
                                imageUrl
                            )

                            viewModel.fetchItems()

                            Toast.makeText(context, "Item added successfully 🚀", Toast.LENGTH_SHORT).show()

                            name = ""
                            price = ""
                            description = ""
                            imageUrl = ""
                            category = ""
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                    ) {
                        Text("Add Item ", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
