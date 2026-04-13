package com.example.smartcanteenapp.ui.admin.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartcanteenapp.model.Order

@Composable
fun OrderCard(
    order: Order,
    onPreparing: () -> Unit,
    onReady: () -> Unit,
    onDone: () -> Unit
) {

    // 🔥 NORMALIZE STATUS (FIXES BUG)
    val status = order.status?.trim()?.uppercase() ?: "UNKNOWN"

    val statusColor = when (status) {
        "PLACED" -> Color(0xFFFF5252)
        "PREPARING" -> Color(0xFFFF9800)
        "READY" -> Color(0xFF4CAF50)
        "COMPLETED" -> Color.Gray
        else -> Color.White
    }

    val statusText = when (status) {
        "PLACED" -> "New Order"
        "PREPARING" -> "Preparing"
        "READY" -> "Ready for Pickup"
        "COMPLETED" -> "Completed"
        else -> "Unknown"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2E1F1A)
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Order #${order.id ?: 0}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "₹${order.totalPrice ?: 0}",
                    color = Color(0xFFFFA726),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Token: ${order.tokenNumber ?: 0}",
                color = Color(0xFFFFCC80)
            )

            Text(
                text = "User ID: ${order.userId ?: 0}",
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (order.items.isNullOrEmpty()) {
                Text("No items", color = Color.Gray)
            } else {
                order.items.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.menuItemName ?: "Item",
                            color = Color.White
                        )

                        Text(
                            text = "x${item.quantity ?: 0}",
                            color = Color(0xFFFFA726)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = statusText,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = order.createdAt
                    ?.replace("T", " ")
                    ?.take(16) ?: "--",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 🔥 FIXED BUTTON FLOW
            if (status != "COMPLETED") {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    if (status == "PLACED") {
                        OutlinedButton(
                            onClick = {
                                println("🔥 CLICK PREPARING")
                                onPreparing()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Preparing")
                        }
                    }

                    if (status == "PREPARING") {
                        OutlinedButton(
                            onClick = {
                                println("🔥 CLICK READY")
                                onReady()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Ready")
                        }
                    }

                    if (status == "READY") {
                        Button(
                            onClick = {
                                println("🔥 CLICK DONE")
                                onDone()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            )
                        ) {
                            Text("Done")
                        }
                    }
                }
            }
        }
    }
}
