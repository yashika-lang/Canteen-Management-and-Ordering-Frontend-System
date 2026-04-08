package com.example.smartcanteenapp.ui.admin

// Removed incorrect import
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartcanteenapp.navigation.Routes
import com.example.smartcanteenapp.ui.admin.components.AdminCard
import com.example.smartcanteenapp.viewmodel.AdminViewModel
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import com.example.smartcanteenapp.ui.admin.components.StatCard

@Composable
fun AdminScreen(navController: NavHostController) {

    val viewModel: AdminViewModel = viewModel()

    // 🔥 API CALL (screen open hote hi)
    LaunchedEffect(Unit) {
        viewModel.fetchItems()
        viewModel.fetchStats()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFF6D4C41)

            )
    ) {

        // 🔥 HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF5D4037))
                .statusBarsPadding()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Hello, Admin 👋",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Manage your canteen smartly",
                    color = Color(0xFFFFCC80),
                    fontSize = 16.sp
                )
            }

            // 🔥 Profile Circle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF9800)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }



        Spacer(modifier = Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFF6D4C41))
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                AdminCard("➕ Add Item", "Create new menu item") {
                    navController.navigate(Routes.ADD_ITEM)
                }
            }

            item {
                AdminCard("📋 Manage Items", "Edit / Delete items") {
                    navController.navigate(Routes.MANAGE_ITEMS)
                }
            }

            item {
                AdminCard("🧾 Orders", "Track & update orders") {
                    navController.navigate(Routes.ORDERS)
                }
            }

            item {
                AdminCard("💳 Payments", "View payment history") {
                    // future navigation
                }
            }
        }

        // 🔥 Banner Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(100.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF4E342E)
            )
        ) {
            val stats = viewModel.stats

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "🔥 Today's Sales",
                    color = Color(0xFFFFCC80),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${stats["totalOrders"] ?: 0} Orders • ₹${stats["revenue"] ?: 0} Earned",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🚀 Motivation Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(90.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF3E2723)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "🚀 Keep Going!",
                    color = Color(0xFFFFCC80),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "More orders, more growth. You're doing great!",
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- Stats UI ---
        val stats = viewModel.stats

        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = Color(0x33FFFFFF),
            thickness = 1.dp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Text(
            text = "Today's Overview",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatCard("Orders", "${stats["totalOrders"] ?: 0}")
            StatCard("Revenue", "₹${stats["revenue"] ?: 0}")
            StatCard("Pending", "${stats["pending"] ?: 0}")
        }
    }
}
