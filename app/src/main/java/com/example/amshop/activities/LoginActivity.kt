package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.amshop.R
import com.example.amshop.app.Endpoint
import com.example.amshop.helper.SessionManager
import com.example.amshop.model.LoginResponse
import com.example.amshop.model.LoginUser
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {

        login_redirect_to_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        login_button_login.setOnClickListener {
            var user = LoginUser(login_edit_text_email.text.toString(), login_edit_text_password.text.toString() )
            var gson = Gson()
            var userJson = gson.toJson(user)
            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(Request.Method.POST, Endpoint.getLoginEndpoint(), JSONObject(userJson),
                {
                    var responseUser = it.getJSONObject("user")
                    SessionManager(this).register(responseUser.getString("_id"), responseUser.getString("firstName"), user.email, user.password)
                    Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()
                    var intent = Intent(this, CategoryListActivity::class.java)
                    SessionManager(this).login(user.email, user.password)
                    startActivity(intent)
                },
                {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                })
            requestQueue.add(request)
        }

    }

}