package com.example.smartcanteenapp.model

data class Order(
    val id: Int?,
    val userId: Int?,
    val status: String?,
    val tokenNumber: Int?,
    val totalPrice: Int?,
    val createdAt: String?,

    // 🔥 NEW$
    val items: List<OrderItem>?,

)