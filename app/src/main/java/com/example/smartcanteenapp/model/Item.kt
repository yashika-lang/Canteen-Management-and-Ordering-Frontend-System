package com.example.smartcanteenapp.model

data class Item(
    val id:  Long,
    val name: String = "",
    val price: Double = 0.0,
    val category: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val available: Boolean = true

)