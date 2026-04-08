package com.example.smartcanteenapp.model

data class Order(

val id: Long?,
val userId: Long?,
val menuItemId: String?,   // 👈 ADD THIS
val quantity: Int,
val status: String,        // 👈 THIS is your orderStatus
val tokenNumber: Int,
val totalPrice: Int
)