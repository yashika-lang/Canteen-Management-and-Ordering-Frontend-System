package com.example.smartcanteenapp.ui.user

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartcanteenapp.viewmodel.AdminViewModel

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.smartcanteenapp.model.Item
import kotlinx.coroutines.*
import org.json.JSONArray
import java.net.URL

@Composable
fun HomeScreen(navController: NavHostController, viewModel: AdminViewModel){

    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
    val userName = sharedPref.getString("name", "User")

    var foodList by remember { mutableStateOf(listOf<Item>()) }
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }


    // 🔥 API CALL
    LaunchedEffect(true) {
        withContext(Dispatchers.IO) {
            try {
                val response = URL("http://192.168.1.40:8080/api/menu").readText()
                val json = JSONArray(response)

                val list = mutableListOf<Item>()

                for (i in 0 until json.length()) {
                    val obj = json.getJSONObject(i)

                    list.add(
                        Item(
                            id = obj.getLong("id"),
                            name = obj.getString("name"),
                            price = obj.getDouble("price"),
                            category = obj.optString("category", ""),
                            description = obj.optString("description", ""),
                            imageUrl = obj.getString("imageUrl"),
                            available = true
                        )
                    )
                }

                withContext(Dispatchers.Main) {
                    foodList = list
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 🔥 FILTER LOGIC
    val filteredList = foodList.filter {
        (selectedCategory == "All" || it.category.equals(selectedCategory, true)) &&
                it.name.contains(searchQuery, true)
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF6D4C41))
                .padding(padding)
        ) {

            // 🔥 HEADER
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF5D4037))
                    .statusBarsPadding()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {

                Text(
                    "Hello, $userName",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "What do you want to eat today?",
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 🔍 SEARCH
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search food") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 🔥 CATEGORIES
                val categories = listOf("All", "Snacks", "Beverages", "Ice Cream", "Breakfast")

                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    categories.forEach { cat ->

                        val selected = cat == selectedCategory

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    if (selected) Color(0xFFFF9800) else Color.White
                                )
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(cat, color = Color.Black)
                        }
                    }
                }
            }

            // 🔥 GRID
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()   // ⭐ THIS IS THE FIX
                    .background(Color(0xFF5D4037)),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = filteredList,
                    key = { it.id }
                ) { item ->
                    FoodCard(item, navController, viewModel)
                }
            }
        }
    }
}

@Composable
fun FoodCard(item: Item, navController: NavHostController, viewModel: AdminViewModel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .clickable {
                viewModel.selectItem(item.id)
                navController.navigate("details/${item.id}")
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {

        Column(modifier = Modifier.padding(12.dp)) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.DarkGray)
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(item.name, color = Color.White, fontWeight = FontWeight.Bold)

            Text("₹ ${item.price}", color = Color.LightGray)

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF9800))
                    .align(Alignment.End)
            ) {
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {

    NavigationBar(containerColor = Color(0xFF5D4037)) {

        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate("home") },
            icon = { Icon(Icons.Default.Home, "") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("cart") },
            icon = { Icon(Icons.Default.ShoppingCart, "") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("profile") },
            icon = { Icon(Icons.Default.Person, "") }
        )
    }
}