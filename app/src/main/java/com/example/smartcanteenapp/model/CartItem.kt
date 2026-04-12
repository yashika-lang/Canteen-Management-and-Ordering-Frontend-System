package com.example.smartcanteenapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class CartItem(
    val item: Item,
    val quantity: Int
) {
    var qty by mutableStateOf(quantity)
}