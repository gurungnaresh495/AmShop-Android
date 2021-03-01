package com.example.amshop.helper

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val FILE_NAME = "File"
    private val USER_NAME = "Name"
    private val USER_EMAIL = "Email"
    private val USER_ID = "id"
    private val USER_PASSWORD = "Password"
    private val USER_STATUS = "LoggedIn"

    private val sharedPreference: SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreference.edit()

    fun register(id: String, name: String, email: String, password:String)
    {
        editor.putString(USER_ID, id)
        editor.putString(USER_EMAIL, email)
        editor.putString(USER_NAME, name)
        editor.putString(USER_PASSWORD, password)
        editor.commit()
    }

    fun login(email: String, password: String): Boolean
    {
        editor.putBoolean(USER_STATUS, true).commit()
        return (email.equals(sharedPreference.getString(USER_EMAIL, "")) && password.equals(sharedPreference.getString(USER_PASSWORD, "")))
    }

    fun isLoggedIn(): Boolean
    {
        return sharedPreference.getBoolean(USER_STATUS, false)
    }

    fun getUser(): String?{
        return sharedPreference.getString(USER_NAME, null)
    }
    fun getUserId(): String?{
        return sharedPreference.getString(USER_ID, null)
    }

    fun getUserEmail(): String?{
        return sharedPreference.getString(USER_EMAIL, null)
    }

    fun logout(){
        editor.remove(USER_STATUS)
        editor.commit()
    }




}