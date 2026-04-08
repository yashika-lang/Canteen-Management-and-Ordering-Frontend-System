package com.example.smartcanteenapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.smartcanteenapp.model.Item
import com.example.smartcanteenapp.model.Order
import com.example.smartcanteenapp.network.RetrofitInstance

class AdminViewModel : ViewModel() {

    // 🔥 ITEMS LIST
    var itemList = mutableStateListOf<Item>()
        private set
    var items by mutableStateOf<List<Item>>(emptyList())
        private set

    // 📦 FETCH ITEMS
    fun fetchItems() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getItems()
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    itemList.clear()
                    itemList.addAll(data)
                    items = data
                } else {
                    println("Fetch Items Failed: ${response.code()}")
                }
            } catch (e: Exception) {
                println("Error Fetching Items: ${e.message}")
            }
        }
    }

    // ➕ ADD ITEM
    fun addItem(
        name: String,
        price: Double,
        category: String,
        description: String,
        imageUrl: String
    ) {
        viewModelScope.launch {
            try {
                val newItem = Item(
                    id = null, // backend generate karega
                    name = name,
                    price = price,
                    category = category,
                    description = description,
                    imageUrl = imageUrl,
                    available = true
                )

                val response = RetrofitInstance.api.addItem(newItem)
                if (response.isSuccessful) {
                    fetchItems()
                } else {
                    println("Add Item Failed: ${response.code()}")
                }

            } catch (e: Exception) {
                println("Error Adding Item: ${e.message}")
            }
        }
    }

    // ❌ DELETE ITEM
    fun deleteItem(item: Item) {
        viewModelScope.launch {
            try {
                item.id?.let {
                    val response = RetrofitInstance.api.deleteItem(it.toLong())
                    if (response.isSuccessful) {
                        itemList.removeAll { i -> i.id == item.id }
                        items = itemList.toList()
                    }
                } ?: println("Item ID is null")
            } catch (e: Exception) {
                println("Error Deleting Item: ${e.message}")
            }
        }
    }

    // 🔄 TOGGLE AVAILABILITY
    fun toggleAvailability(item: Item, newValue: Boolean) {
        println("🔥 Toggle clicked: ${item.id} -> $newValue")

        viewModelScope.launch {
            try {
                item.id?.let { id ->
                    val response = RetrofitInstance.api.updateAvailability(id.toLong(), newValue)

                    println("🔥 API response: ${response.code()}")

                    if (response.isSuccessful) {
                        // Optimistic UI update + refresh
                        val updatedList = itemList.map {
                            if (it.id == item.id) it.copy(available = newValue) else it
                        }
                        itemList.clear()
                        itemList.addAll(updatedList)
                        items = updatedList

                        fetchItems()
                    } else {
                        println("Toggle Failed: ${response.code()}")
                    }

                } ?: println("Item ID is null")
            } catch (e: Exception) {
                println("Error Updating Item: ${e.message}")
            }
        }
    }

    // 🧾 ORDERS LIST
    var orderList = mutableStateListOf<Order>()
        private set

    // 📦 FETCH ORDERS
    fun fetchOrders() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getOrders()
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    orderList.clear()
                    orderList.addAll(data)
                    println("ORDERS FETCHED: ${data.size}")
                } else {
                    println("Fetch Orders Failed: ${response.code()}")
                }
            } catch (e: Exception) {
                println("Error Fetching Orders: ${e.message}")
            }
        }
    }

    var stats by mutableStateOf<Map<String, Any>>(emptyMap())
        private set

    fun fetchStats() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAdminStats()
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyMap()
                    stats = data
                    println("STATS: $data")
                }
            } catch (e: Exception) {
                println("Stats error: ${e.message}")
            }
        }
    }

    // 🔄 UPDATE ORDER STATUS (LOCAL + later backend)
    fun updateOrderStatus(order: Order, status: String) {
        viewModelScope.launch {
            try {
                val orderId = order.id?.toLong() ?: return@launch
                val userId = order.userId?.toString() ?: "1"

                val body = mapOf(
                    "status" to status,
                    "userId" to userId
                )

                val response = RetrofitInstance.api.updateOrderStatus(orderId, body)

                if (response.isSuccessful) {
                    fetchOrders()
                } else {
                    println("Update failed: ${response.code()}")
                }

            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}