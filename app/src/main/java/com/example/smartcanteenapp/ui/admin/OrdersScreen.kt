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
import com.example.smartcanteenapp.ui.admin.components.OrderCard

@Composable
fun OrdersScreen(navController: NavHostController, viewModel: AdminViewModel) {

    val orders = viewModel.allOrders.toList()

    var lastOrderCount by remember { mutableStateOf(0) }

    // 🔥 ONLY FETCH ONCE (NO LOOP)
    LaunchedEffect(Unit) {
        println("🔥 ADMIN SCREEN STARTED")
        viewModel.fetchOrders()
    }

    LaunchedEffect(orders.size) {
        if (orders.size > lastOrderCount && lastOrderCount != 0) {
            println("🔥 NEW ORDER RECEIVED!")

            android.media.ToneGenerator(
                android.media.AudioManager.STREAM_NOTIFICATION,
                100
            ).startTone(android.media.ToneGenerator.TONE_PROP_BEEP)
        }
        lastOrderCount = orders.size
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Orders Dashboard",
                    fontSize = 24.sp,
                    color = Color.White
                )

                Text(
                    text = "${orders.size} orders",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )

                if (orders.any { it.status == "PLACED" }) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(Color.Red, RoundedCornerShape(50))
                    )
                }
            }

            // 🔥 LIVE STATUS COUNTERS
            val readyCount = orders.count { (it.status ?: "").trim().uppercase() == "READY" }
            val completedCount = orders.count { (it.status ?: "").trim().uppercase() == "COMPLETED" }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0x3329B6F6)
                ) {
                    Text(
                        text = "Ready: $readyCount",
                        color = Color(0xFF29B6F6),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0x334CAF50)
                ) {
                    Text(
                        text = "Completed: $completedCount",
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

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

                    val context = androidx.compose.ui.platform.LocalContext.current

                    OrderCard(
                        order = order,
                        onPreparing = {
                            viewModel.updateOrderStatus(order, "PREPARING")
                            android.widget.Toast.makeText(context, "Order set to Preparing", android.widget.Toast.LENGTH_SHORT).show()
                        },
                        onReady = {
                            viewModel.updateOrderStatus(order, "READY")
                            android.widget.Toast.makeText(context, "Order marked Ready ✅", android.widget.Toast.LENGTH_SHORT).show()
                        },
                        onDone = {
                            viewModel.updateOrderStatus(order, "COMPLETED")
                            android.widget.Toast.makeText(context, "Order Completed 🎉", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}
