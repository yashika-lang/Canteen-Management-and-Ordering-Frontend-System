package com.example.smartcanteenapp.ui.admin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcanteenapp.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun ItemCard(
    item: Item,
    onDelete: () -> Unit,
    onToggle: (Boolean) -> Unit
) {

    var bitmap by remember { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var isAvailable by remember { mutableStateOf(item.available) }

    // 🔥 LOAD IMAGE FROM URL
    LaunchedEffect(item.imageUrl) {
        withContext(Dispatchers.IO) {
            try {
                val stream = URL(item.imageUrl).openStream()
                val bmp = BitmapFactory.decodeStream(stream)

                withContext(Dispatchers.Main) {
                    bitmap = bmp?.asImageBitmap()
                }
            } catch (_: Exception) {
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {

            // 🔥 IMAGE
            if (bitmap != null) {
                Image(
                    bitmap = bitmap!!,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(14.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.DarkGray)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = item.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Text(
                    text = "₹${item.price}",
                    color = Color(0xFFFFCC80),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 🔥 AVAILABILITY SWITCH
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = if (isAvailable) "Available" else "Out",
                        color = if (isAvailable) Color(0xFF4CAF50) else Color.Red
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Switch(
                        checked = isAvailable,
                        onCheckedChange = {
                            isAvailable = it
                            onToggle(it)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFFFF9800),
                            checkedTrackColor = Color(0x55FF9800)
                        )
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {

                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        tint = Color(0xFFFF9800)
                    )
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onDelete()
                    }
                ) {
                    Text("Delete", color = Color(0xFFFF9800))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel", color = Color.White)
                }
            },
            title = {
                Text("Delete Item?", color = Color.White)
            },
            text = {
                Text("Are you sure you want to delete this item?", color = Color.LightGray)
            },
            containerColor = Color.Black
        )
    }
}