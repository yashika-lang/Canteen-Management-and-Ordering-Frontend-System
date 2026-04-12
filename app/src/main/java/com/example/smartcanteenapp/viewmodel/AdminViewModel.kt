package com.example.smartcanteenapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.smartcanteenapp.dto.AddItemRequest
import com.example.smartcanteenapp.dto.CreateOrderRequest
import com.example.smartcanteenapp.dto.OrderItemRequest
import com.example.smartcanteenapp.model.CartItem
import kotlinx.coroutines.launch
import com.example.smartcanteenapp.model.Item
import com.example.smartcanteenapp.model.Order
import com.example.smartcanteenapp.network.RetrofitInstance
import kotlin.collections.emptyList

class AdminViewModel : ViewModel() {

    // 🔥 ITEMS LIST
    var itemList = mutableStateListOf<Item>()
        private set
    var selectedItem by mutableStateOf<Item?>(null)
        private set
    var currentUserId by mutableStateOf<Long?>(null)

    var allOrders = mutableStateListOf<Order>()
    var userOrders = mutableStateListOf<Order>()


    fun selectItem(id: Long) {
        selectedItem = itemList.find { it.id == id }
    }


    // 📦 FETCH ITEMS
    fun fetchItems() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getItems()
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    itemList.clear()
                    itemList.addAll(data)
                } else {
                    println("Fetch Items Failed: ${response.code()}")
                }
            } catch (e: Exception) {
                println("Error Fetching Items: ${e.message}")
            }
        }
    }
    fun placeOrder() {
        viewModelScope.launch {
            try {

                if (cartItems.isEmpty()) {
                    println("❌ Cart is empty")
                    return@launch
                }

                val userId = currentUserId ?: return@launch

                val itemsList = cartItems.map {
                    OrderItemRequest(
                        menuItemId = it.item.id,
                        quantity = it.qty
                    )
                }

                val request = CreateOrderRequest(
                    userId = userId,
                    items = itemsList
                )

                println("🔥 Sending MULTI ITEM order: $request")

                val response = RetrofitInstance.api.createOrder(request)

                println("🔥 RESPONSE: ${response.code()}")

                if (response.isSuccessful) {
                    cartItems.clear()
                    fetchUserOrders(userId)
                    fetchOrders()
                } else {
                    println("❌ Failed to place order: ${response.code()}")
                }

            } catch (e: Exception) {
                println("❌ Error: ${e.message}")
            }
        }
    }
    fun fetchUserOrders(userId: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getUserOrders(userId)
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()

                    userOrders.clear()
                    userOrders.addAll(data)

                    println("🔥 USER ORDERS FETCHED: ${data.size}")
                    data.forEach { println("👉 Order: $it") }
                } else {
                    println("❌ USER ORDERS API FAILED: ${response.code()}")
                }
            } catch (e: Exception) {
                println("❌ ERROR: ${e.message}")
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
                val newItem = AddItemRequest(// backend generate karega
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
                        val index = itemList.indexOfFirst { it.id == item.id }
                        if (index != -1) {
                            itemList[index] = itemList[index].copy(available = newValue)
                        }
                    } else {
                        println("Toggle Failed: ${response.code()}")
                    }

                } ?: println("Item ID is null")
            } catch (e: Exception) {
                println("Error Updating Item: ${e.message}")
            }
        }
    }

    // 📦 FETCH ORDERS
    fun fetchOrders() {
        viewModelScope.launch {
            try {
                println("🔥 FETCHING ADMIN ORDERS...")
                val response = RetrofitInstance.api.getOrders()
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    allOrders.clear()
                    allOrders.addAll(data)
                    println("ALL ORDERS FETCHED: ${data.size}")
                    println("🔥 ADMIN ORDERS SIZE: ${data.size}")
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
    var cartItems = mutableStateListOf<CartItem>()
    fun addToCart(item: Item, quantity: Int) {
        val existing = cartItems.find { it.item.id == item.id }

        if (existing != null) {
            existing.qty += quantity
        } else {
            cartItems.add(CartItem(item, quantity))
        }
    }

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.item.price * it.qty }
    }


    // 🔄 UPDATE ORDER STATUS (LOCAL + later backend)
    fun updateOrderStatus(order: Order, status: String) {
        viewModelScope.launch {
            try {
                val orderId = order.id?.toLong() ?: return@launch
                val userId = order.userId?.toString() ?: return@launch

                val body = mapOf(
                    "status" to status,
                    "userId" to userId
                )

                val response = RetrofitInstance.api.updateOrderStatus(orderId, body)

                if (response.isSuccessful) {
                    fetchOrders()
                    currentUserId?.let { fetchUserOrders(it) }
                } else {
                    println("Update failed: ${response.code()}")
                }

            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}