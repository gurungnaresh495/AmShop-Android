package com.example.amshop.app

import java.util.*

class Endpoint {

    companion object{
        private const val CATEGORY = "category"
        private const val SUB_CATEGORY = "subcategory/"
        private const val PRODUCT_SUB = "products/sub/"
        private const val PRODUCT = "products/"
        private const val LOGIN = "auth/login"
        private const val REGISTER = "auth/register"
        private const val ADDRESS = "address/"
        private const val ORDER = "orders/"
        private const val USER = "users/"

        fun getCategoryEndpoint() : String
        {
            return Config.BASE_URL + CATEGORY
        }

        fun getSubCategoryEndpoint( catId: Int): String
        {
            return Config.BASE_URL+ SUB_CATEGORY + catId.toString()
        }

        fun getProductEndpoint( subCatId: Int): String
        {
            return Config.BASE_URL + PRODUCT_SUB + subCatId.toString()
        }

        fun getSingleProductEndpoint(id: String): String
        {
            return Config.BASE_URL + PRODUCT + id
        }

        fun getLoginEndpoint(): String
        {
            return Config.BASE_URL + LOGIN
        }

        fun getRegisterEndpoint(): String
        {
            return Config.BASE_URL + REGISTER
        }
        fun getAddressEndpoint(): String
        {
            return Config.BASE_URL + ADDRESS
        }

        fun getOrdersEndpoint(): String
        {
            return Config.BASE_URL + ORDER
        }

        fun getUsersEndpoint(): String
        {
            return Config.BASE_URL + USER
        }
    }
}