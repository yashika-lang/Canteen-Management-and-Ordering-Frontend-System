package com.example.smartcanteenapp.ui.admin

import androidx.navigation.NavHostController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartcanteenapp.viewmodel.AdminViewModel
import com.example.smartcanteenapp.ui.admin.components.OrderCard
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun OrdersScreen(navController: NavHostController) {

    val viewModel: AdminViewModel = viewModel()

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.fetchOrders()
            kotlinx.coroutines.delay(5000) // auto refresh every 5 sec
        }
    }

    val orders = viewModel.orderList

    // 🔘 FILTER STATE
    var selectedFilter by remember { mutableStateOf("ALL") }

    val filteredOrders = when (selectedFilter) {
        "NEW" -> orders.filter { it.status == "PLACED" }
        "PROCESSING" -> orders.filter { it.status == "PREPARING" }
        "DELIVERED" -> orders.filter { it.status == "COMPLETED" }
        else -> orders
    }

    var lastCount by remember { mutableStateOf(0) }

    LaunchedEffect(orders.size) {
        if (orders.size > lastCount && lastCount != 0) {
            println("🔥 New Order Received!")
        }
        lastCount = orders.size
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // 🔝 HEADER + FILTERS
        Column(modifier = Modifier.padding(16.dp)) {

            Spacer(modifier = Modifier.height(8.dp))

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
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Orders Received",
                        fontSize = 24.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )

                    if (orders.any { it.status == "PLACED" }) {
                        Spacer(modifier = Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(Color.Red, shape = RoundedCornerShape(50))
                        )
                    }
                }

                Text(
                    text = "${filteredOrders.size} orders",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FilterChip("ALL", selectedFilter) { selectedFilter = "ALL" }
                FilterChip("NEW", selectedFilter) { selectedFilter = "NEW" }
                FilterChip("PROCESSING", selectedFilter) { selectedFilter = "PROCESSING" }
                FilterChip("DELIVERED", selectedFilter) { selectedFilter = "DELIVERED" }
            }
        }

        if (filteredOrders.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No Orders Yet")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 4.dp,
                    bottom = 20.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredOrders) { order ->

                    val context = androidx.compose.ui.platform.LocalContext.current

                    OrderCard(
                        order = order,
                        onPreparing = {
                            viewModel.updateOrderStatus(order, "PREPARING")
                            android.widget.Toast.makeText(context, "Order is being prepared", android.widget.Toast.LENGTH_SHORT).show()
                        },
                        onReady = {
                            viewModel.updateOrderStatus(order, "READY")
                            android.widget.Toast.makeText(context, "Order is ready for pickup", android.widget.Toast.LENGTH_SHORT).show()
                        },
                        onDone = {
                            viewModel.updateOrderStatus(order, "COMPLETED")
                            android.widget.Toast.makeText(context, "Order completed", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FilterChip(
    label: String,
    selected: String,
    onClick: () -> Unit
) {
    val isSelected = selected == label

    val scale by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        label = "chipScale"
    )

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = if (isSelected) 4.dp else 0.dp,
        modifier = Modifier.scale(scale)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = label,
                fontSize = 13.sp,
                maxLines = 1,
                softWrap = false,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}