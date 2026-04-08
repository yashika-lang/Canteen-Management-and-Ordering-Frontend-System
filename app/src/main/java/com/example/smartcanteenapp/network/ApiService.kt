package com.example.smartcanteenapp.network

import com.example.smartcanteenapp.model.Item
import com.example.smartcanteenapp.model.Order
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("api/menu")
    suspend fun getItems(): Response<List<Item>>

    @POST("api/menu")
    suspend fun addItem(@Body item: Item): Response<Item>

    @DELETE("api/menu/{id}")
    suspend fun deleteItem(@Path("id") id: Long): Response<Unit>

    @PUT("api/menu/{id}")
    suspend fun updateItem(
        @Path("id") id: Long,
        @Body item: Item
    ): Response<Item>

    @GET("api/order")
    suspend fun getOrders(): Response<List<Order>>

    @POST("api/order")
    suspend fun createOrder(@Body request: Map<String, Any>): Response<Order>

    @PATCH("api/order/{id}/status")
    suspend fun updateOrderStatus(
        @Path("id") id: Long,
        @Body body: Map<String, String>
    ): Response<Unit>

    @GET("api/order/admin/stats")
    suspend fun getAdminStats(): Response<Map<String, Any>>

    @PUT("api/menu/{id}/availability")
    suspend fun updateAvailability(
        @Path("id") id: Long,
        @Query("available") available: Boolean
    ): Response<Unit>
}
