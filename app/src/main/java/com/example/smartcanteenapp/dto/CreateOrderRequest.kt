package com.example.smartcanteenapp.dto

    data class CreateOrderRequest(
val userId: Long,
val items: List<OrderItemRequest>
)
