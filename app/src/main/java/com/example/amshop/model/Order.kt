package com.example.amshop.model

import java.io.Serializable

data class Order(
    val date: String,
    val orderStatus: String,
    val orderSummary: OrderSummary,
    val products: List<Product>,
    val shippingAddress: Address,
    val user: UserXX,
    val userId: String
): Serializable
{
    companion object{
        const val ORDER_KEY = "Order"
    }
}

data class OrderResponse(
    val error: String,
    val count: Int,
    val data : ArrayList<Order>
)