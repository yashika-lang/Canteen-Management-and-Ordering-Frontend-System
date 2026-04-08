package com.example.smartcanteenapp.ui.admin.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcanteenapp.model.Order

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
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Order #${order.id}",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "User ID: ${order.userId ?: "Unknown"}",
                color = Color.LightGray
            )

            Text(
                text = "Item: ${order.menuItemId}",
                color = Color.LightGray
            )

            Text(
                text = "Qty: ${order.quantity}",
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Amount: ₹${order.totalPrice}",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Status: ${order.status}",
                color = when (order.status) {
                    "PLACED" -> Color.Yellow
                    "PREPARING" -> Color(0xFFFF9800)
                    "READY" -> Color.Green
                    else -> Color.White
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = onPreparing,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800)
                    )
                ) {
                    Text("Prep")
                }

                Button(
                    onClick = onReady,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Text("Ready")
                }

                Button(
                    onClick = onDone,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Text("Done")
                }
            }
        }
    }
}