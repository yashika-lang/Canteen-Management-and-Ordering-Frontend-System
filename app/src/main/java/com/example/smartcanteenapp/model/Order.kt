package com.example.smartcanteenapp.model

data class Order(
val id: Int?,
val menuItemId: Int?,
val menuItemName: String?,
val quantity: Int?,
val status: String?,
val tokenNumber: Int?,
val totalPrice: Int?,
val userId: Int?,
val createdAt: String?
)