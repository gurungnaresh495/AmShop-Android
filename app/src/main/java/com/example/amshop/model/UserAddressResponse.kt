package com.example.amshop.model

import java.io.Serializable

data class UserAddressResponse(
    val count: Int,
    val data: List<Address>,
    val error: Boolean
)

data class Address(
    val _id: String?,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String
): Serializable
{
    companion object{
        const val ADDRESS_KEY = "Address"
    }
}