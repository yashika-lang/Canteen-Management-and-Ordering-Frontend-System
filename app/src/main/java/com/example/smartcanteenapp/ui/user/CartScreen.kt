package com.example.smartcanteenapp.ui.user

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.smartcanteenapp.viewmodel.AdminViewModel
import com.example.smartcanteenapp.model.CartItem
import android.app.Activity
import android.content.Context
import android.widget.Toast
import org.json.JSONObject
import com.razorpay.Checkout

@Composable
fun CartScreen(
    navController: NavHostController,
    viewModel: AdminViewModel
) {

    val cartItems = viewModel.cartItems
    val total = viewModel.getTotalPrice()

    LaunchedEffect(Unit) {
        if (com.example.smartcanteenapp.viewmodel.PaymentState.paymentSuccess) {
            viewModel.placeOrder()
            com.example.smartcanteenapp.viewmodel.PaymentState.paymentSuccess = false
            navController.navigate("success")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF795548),
                        Color(0xFF3E2723)
                    )
                )
            )
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Order Details",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF4E342E),
                            Color(0xFF2E1F1A)
                        )
                    ),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .padding(12.dp)
        ) {
            LazyColumn {

                items(cartItems) { cartItem ->

                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            AsyncImage(
                                model = cartItem.item.imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(75.dp)
                                    .background(Color.DarkGray, RoundedCornerShape(12.dp))
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(
                                    text = cartItem.item.name,
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text(
                                    text = "₹ ${cartItem.item.price}",
                                    color = Color(0xFFFFA726)
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFFFFA726), RoundedCornerShape(50))
                                        .clickable {
                                            if (cartItem.qty > 1) {
                                                cartItem.qty--
                                            } else {
                                                viewModel.cartItems.remove(cartItem)
                                            }
                                            viewModel.cartItems = viewModel.cartItems.toMutableStateList()
                                        }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text("-", color = Color.Black)
                                }

                                Text(
                                    text = "${cartItem.qty}",
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )

                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFFFFA726), RoundedCornerShape(50))
                                        .clickable {
                                            viewModel.addToCart(cartItem.item, 1)
                                        }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text("+", color = Color.Black)
                                }
                            }
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total", color = Color.White)
            Text("₹ ${"%.2f".format(total)}", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                startPayment(
                    navController.context as Activity,
                    total
                ) {
                    viewModel.placeOrder()
                    navController.navigate("success")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF689F38)
            ),
            shape = RoundedCornerShape(30.dp),
            elevation = ButtonDefaults.buttonElevation(8.dp),
        ) {
            Text(
                "Pay Now 💳",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                Toast.makeText(navController.context, "Please complete payment first 💳", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF9800)
            ),
            shape = RoundedCornerShape(30.dp),
            elevation = ButtonDefaults.buttonElevation(8.dp),
        ) {
            Text(
                "CHECKOUT",
                color = Color(0xFF3E2723),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

fun startPayment(
    activity: Activity,
    amount: Double,
    onSuccess: () -> Unit
) {

    val checkout = Checkout()
    checkout.setKeyID("rzp_test_ScmnCBPX0OlLXG")

    try {
        val options = JSONObject()
        options.put("name", "Smart Canteen")
        options.put("description", "Food Order Payment")
        options.put("currency", "INR")
        options.put("amount", (amount * 100).toInt())

        checkout.open(activity, options)

        // ⚠️ NOTE: Razorpay success callback Activity me aata hai
        // so we will trigger manually (demo hack)

    } catch (e: Exception) {
        Toast.makeText(activity, "Payment Error", Toast.LENGTH_SHORT).show()
    }
}