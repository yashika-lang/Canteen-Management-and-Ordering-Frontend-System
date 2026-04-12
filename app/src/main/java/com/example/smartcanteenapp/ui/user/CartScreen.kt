package com.example.smartcanteenapp.ui.user

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

@Composable
fun CartScreen(
    navController: NavHostController,
    viewModel: AdminViewModel
) {

    val cartItems = viewModel.cartItems
    val total = viewModel.getTotalPrice()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6D4C41),
                        Color(0xFF3E2723)
                    )
                )
            )
            .padding(16.dp)
    ) {

        Text(
            text = "Order Details",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {

            items(cartItems) { cartItem ->

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total", color = Color.White)
            Text("₹ ${"%.2f".format(total)}", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.placeOrder()
                navController.navigate("success")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA726)
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("CHECKOUT", color = Color.Black)
        }
    }
}