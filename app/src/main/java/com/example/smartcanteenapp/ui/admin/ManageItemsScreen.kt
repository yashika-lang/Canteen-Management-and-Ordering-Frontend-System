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

@Composable
fun ManageItemsScreen() {

    val viewModel: AdminViewModel = viewModel()
    val items = viewModel.itemList

    // 🔥 MOST IMPORTANT LINE
    LaunchedEffect(Unit) {
        viewModel.fetchItems()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF6D4C41))

            .padding(16.dp)
    ) {

        Text(
            text = "Manage Items",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Control availability & manage menu",
            color = Color(0xFFFFCC80),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Divider(
            color = Color(0x33FFFFFF),
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
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
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