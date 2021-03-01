package com.example.amshop.model

import java.io.Serializable

data class LoginUser(
    val email: String,
    val password: String
): Serializable

 data class RegisterUser(
    val email: String,
    val firstName: String,
    val mobile: String,
    val password: String
): Serializable