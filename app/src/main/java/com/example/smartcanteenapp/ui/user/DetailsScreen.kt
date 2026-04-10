package com.example.smartcanteenapp.ui.user

import android.graphics.BitmapFactory
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.smartcanteenapp.model.Item
import kotlinx.coroutines.*
import org.json.JSONArray
import java.net.URL

@Composable
fun DetailsScreen(
    navController: NavHostController,
    itemId: Int
){


    var item by remember { mutableStateOf<Item?>(null) }
    var quantity by remember { mutableStateOf(1) }
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(itemId) {
        try {
            val response = withContext(Dispatchers.IO) {
                URL("http://192.168.1.40:8080/api/menu").readText()
            }

            val json = JSONArray(response)

            for (i in 0 until json.length()) {
                val obj = json.getJSONObject(i)

                if (obj.getInt("id") == itemId) {

                    val found = Item(
                        id = obj.getLong("id"),
                        name = obj.getString("name"),
                        price = obj.getDouble("price"),
                        category = obj.optString("category", "Snacks"),
                        description = obj.optString("description", ""),
                        imageUrl = obj.getString("imageUrl"),
                        available = obj.optBoolean("available", true)
                    )

                    val bmp = withContext(Dispatchers.IO) {
                        BitmapFactory.decodeStream(URL(found.imageUrl).openStream())
                    }

                    item = found
                    bitmap = bmp?.asImageBitmap()
                    break
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    if (item == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2B1B16)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    } else {
        val food = item!!

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2B1B16))
        ) {

            Column {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {

                    bitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        Color(0xFF2B1B16)
                                    )
                                )
                            )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0x66FFFFFF))
                                .clickable { navController.popBackStack() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.ArrowBack, "", tint = Color.White)
                        }

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0x66FFFFFF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.ShoppingCart, "", tint = Color.White)
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    Text(food.name, color = Color.White, fontSize = 26.sp)

                    Text("₹ ${food.price}", color = Color(0xFFFF9800), fontSize = 20.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Chip("4.5 ⭐")
                        Chip("10 mins")
                        Chip(food.category)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(Color.Black)
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Text(
                                    "-",
                                    color = Color.White,
                                    modifier = Modifier
                                        .clickable { if (quantity > 1) quantity-- }
                                        .padding(8.dp)
                                )

                                Text(
                                    quantity.toString(),
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )

                                Text(
                                    "+",
                                    color = Color.White,
                                    modifier = Modifier
                                        .clickable { quantity++ }
                                        .padding(8.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text("About Food", color = Color.White, fontSize = 16.sp)

                    Text(
                        text = food.description.ifEmpty { "No description available" },
                        color = Color.LightGray
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFF9800))
                    ) {
                        Text("ADD TO CART", color = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun Chip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0x33FFFFFF))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}