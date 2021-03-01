package com.example.amshop.model

import java.io.Serializable

data class OrderSummary(
    val _id: String?,
    val deliveryCharges: Double?,
    val discount: Double,
    val orderAmount: Double,
    val ourPrice: Double,
    val totalAmount: Double
): Serializable