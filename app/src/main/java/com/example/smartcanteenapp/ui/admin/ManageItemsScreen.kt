package com.example.smartcanteenapp.ui.admin

import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartcanteenapp.viewmodel.AdminViewModel
import com.example.smartcanteenapp.ui.admin.components.ItemCard
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Brush
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun ManageItemsScreen(
    navController: NavHostController,
    viewModel: AdminViewModel
) {

    val items = viewModel.itemList

    // 🔥 MOST IMPORTANT LINE
    LaunchedEffect(Unit) {
        viewModel.fetchItems()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF5D4037),
                        Color(0xFF3E2723)
                    )
                )
            )
            .padding(16.dp)
    ) {

        // 🔙 BACK BUTTON (TOP)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        // 🧾 TITLE BELOW
        Text(
            text = "Manage Items",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Control availability & manage menu",
            color = Color(0xFFFFCC80),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        Divider(
            color = Color(0x22FFFFFF),
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (items.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "No items yet 😕",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(items) { item ->

                    ItemCard(
                        item = item,
                        onDelete = { viewModel.deleteItem(item) },
                        onToggle = { newValue ->
                            viewModel.toggleAvailability(item, newValue)
                        }
                    )
                }
            }
        }
    }
}