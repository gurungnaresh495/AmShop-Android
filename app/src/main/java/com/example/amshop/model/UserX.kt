package com.example.amshop.model

import java.io.Serializable

data class UserResponse(
    val error : Boolean,
    val data : UserX
)

data class UserX(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val firstName: String,
    val mobile: String,
    val password: String,
    val email: String? = null
): Serializable