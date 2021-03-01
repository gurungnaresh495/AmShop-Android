package com.example.amshop.model

data class LoginResponse(
    val token: String,
    val user: UserX
)