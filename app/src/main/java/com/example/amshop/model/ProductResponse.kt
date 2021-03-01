package com.example.amshop.model

import java.io.Serializable

data class ProductResponse(
    val count: Int,
    val data: List<Product>,
    val error: Boolean
)

data class SingleProductResponse(
    val error: Boolean,
    val data: Product
)

data class Product(
    val _id: String,
    val catId: Int,
    val description: String,
    val image: String,
    val mrp: Double,
    val price: Double,
    val productName: String,
    val quantity: Int,
    val subId: Int
): Serializable
{
    companion object{
        const val PRODUCT_KEY = "Product"
    }
}