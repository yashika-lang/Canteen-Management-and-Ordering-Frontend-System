package com.example.smartcanteenapp.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartcanteenapp.viewmodel.AdminViewModel

@Composable
fun UserOrdersScreen(viewModel: AdminViewModel){
    LaunchedEffect(viewModel.currentUserId) {
        viewModel.currentUserId?.let {
            viewModel.fetchUserOrders(it)
        }
    }

    val orders = viewModel.userOrders

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF6D4C41),
                        Color(0xFF3E2723)
                    )
                )
            )
            .padding(16.dp)
    ) {

        Text(
            text = "My Orders",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (orders.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No orders yet", color = Color.White)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(orders) { order ->

                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                        elevation = CardDefaults.cardElevation(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {

                        Column(modifier = Modifier.padding(16.dp)) {

                            // 🔥 HEADER
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Token #${order.tokenNumber ?: 0}",
                                    color = Color(0xFFFFA726),
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Box(
                                    modifier = Modifier
                                        .background(
                                            when (order.status ?: "") {
                                                "PLACED" -> Color(0xFFFFA726)
                                                "PREPARING" -> Color(0xFF29B6F6)
                                                "READY" -> Color(0xFF66BB6A)
                                                "COMPLETED" -> Color.Gray
                                                else -> Color.DarkGray
                                            },
                                            RoundedCornerShape(12.dp)
                                        )
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = order.status ?: "UNKNOWN",
                                        color = Color.Black,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Divider(color = Color.DarkGray)

                            Spacer(modifier = Modifier.height(10.dp))

                            // 🔥 MULTI ITEMS SUPPORT
                            if (order.items.isNullOrEmpty()) {
                                Text(
                                    text = "No items",
                                    color = Color.Gray
                                )
                            } else {
                                order.items.forEach { item ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = item.menuItemName ?: "Item",
                                            color = Color.White
                                        )

                                        Text(
                                            text = "x${item.quantity ?: 0}",
                                            color = Color.LightGray
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            // 💰 TOTAL
                            Text(
                                text = "₹ ${order.totalPrice ?: 0}",
                                color = Color(0xFFFFA726),
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            val formattedTime = order.createdAt
                                ?.takeIf { it.length >= 16 }
                                ?.substring(11, 16) ?: "--:--"

                            Text(
                                text = "🕒 $formattedTime",
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}