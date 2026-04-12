package com.example.smartcanteenapp.ui.admin.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcanteenapp.model.Order
/*
@Composable
fun OrderCard(
    order: Order,
    onPreparing: () -> Unit,
    onReady: () -> Unit,
    onDone: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2E1F1A)
        )
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            // 👇 TOP BREATHING SPACE (THIS FIXES YOUR ISSUE)
            Spacer(modifier = Modifier.height(10.dp))

            // 🔥 HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Order #${order.id ?: 0}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = "Token ${order.tokenNumber ?: 0}",
                    color = Color(0xFFFFB74D),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "User ID: ${order.userId ?: 0}",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(6.dp))

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
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.menuItemName ?: "Item",
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = "x${item.quantity ?: 0}",
                            color = Color.LightGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Total: ₹${order.totalPrice ?: 0}",
                color = Color(0xFFFFAB40),
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            val statusText = when (order.status ?: "") {
                "PLACED" -> "New Order"
                "PREPARING" -> "Preparing"
                "READY" -> "Ready for Pickup"
                "COMPLETED" -> "Completed"
                else -> order.status ?: "Unknown"
            }

            val statusColor = when (order.status ?: "") {
                "PLACED" -> Color(0xFFFF5252)
                "PREPARING" -> Color(0xFFFF9800)
                "READY" -> Color(0xFF4CAF50)
                "COMPLETED" -> Color.Gray
                else -> Color.White
            }

            Text(
                text = "Status: $statusText",
                color = statusColor,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Time: ${order.createdAt?.replace("T", " ")?.take(16) ?: "--"}",
                color = Color.LightGray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 🔘 BUTTONS (SMART FLOW)
            if (order.status != "COMPLETED") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    if (order.status == "PLACED") {
                        OutlinedButton(
                            onClick = onPreparing,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Preparing")
                        }
                    }

                    if (order.status == "PREPARING") {
                        OutlinedButton(
                            onClick = onReady,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Ready")
                        }
                    }

                    if (order.status == "READY") {
                        Button(
                            onClick = onDone,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                        ) {
                            Text("Done")
                        }
                    }
                }
            }
        }
    }
}
*/