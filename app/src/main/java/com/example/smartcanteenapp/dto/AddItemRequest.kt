package com.example.smartcanteenapp.dto

data class AddItemRequest(
    val name: String,
    val price: Double,
    val category: String,
    val description: String,
    val imageUrl: String,
    val available: Boolean
)