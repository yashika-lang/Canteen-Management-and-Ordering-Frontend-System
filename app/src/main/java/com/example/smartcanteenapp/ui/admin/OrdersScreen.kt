package com.example.smartcanteenapp.ui.admin

import androidx.navigation.NavHostController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcanteenapp.viewmodel.AdminViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun OrdersScreen(navController: NavHostController, viewModel: AdminViewModel) {

    val orders = viewModel.allOrders

    // 🔥 ONLY FETCH ONCE (NO LOOP)
    LaunchedEffect(Unit) {
        println("🔥 ADMIN SCREEN STARTED")
        viewModel.fetchOrders()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3E2723))
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Orders Dashboard",
                fontSize = 24.sp,
                color = Color.White
            )

            // 🔥 DEBUG COUNT
            Text(
                text = "Orders Count: ${orders.size}",
                color = Color.Green
            )

            Spacer(modifier = Modifier.height(12.dp))

        }

        if (orders.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No Orders Yet", color = Color.White)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(orders) { order ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1E1E1E)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                text = "Order #${order.id}",
                                color = Color.White
                            )

                            Text(
                                text = "User: ${order.userId}",
                                color = Color.LightGray
                            )

                            Text(
                                text = "Status: ${order.status}",
                                color = Color.Gray
                            )

                            Text(
                                text = "Total: ₹${order.totalPrice}",
                                color = Color(0xFFFFA726)
                            )
                        }
                    }
                }
            }
        }
    }
}
