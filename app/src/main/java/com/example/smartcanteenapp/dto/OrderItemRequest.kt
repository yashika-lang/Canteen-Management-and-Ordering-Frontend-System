package com.example.smartcanteenapp.dto

data class OrderItemRequest(
    val menuItemId: Long,
    val quantity: Int
)