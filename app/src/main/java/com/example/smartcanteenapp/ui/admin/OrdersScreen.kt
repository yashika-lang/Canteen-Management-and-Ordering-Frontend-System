package com.example.smartcanteenapp.ui.admin


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartcanteenapp.viewmodel.AdminViewModel
import com.example.smartcanteenapp.ui.admin.components.OrderCard

@Composable
fun OrdersScreen() {

    val viewModel: AdminViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.fetchOrders()
    }

    val orders = viewModel.orderList

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(orders) { order ->

            OrderCard(
                order = order,
                onPreparing = {
                    viewModel.updateOrderStatus(order.id?.toLong() ?: return@OrderCard, "PREPARING")
                },
                onReady = {
                    viewModel.updateOrderStatus(order.id?.toLong() ?: return@OrderCard, "READY")
                },
                onDone = {
                    viewModel.updateOrderStatus(order.id?.toLong() ?: return@OrderCard, "COMPLETED")
                }
            )
        }
    }
}