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
import com.example.amshop.model.RegisterUser
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {

        register_redirect_to_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        register_register_button.setOnClickListener {
            var user = RegisterUser(
                register_edit_text_email.text.toString(),
                register_edit_text_firstname.text.toString(),
                register_edit_text_phone.text.toString(),
                register_edit_text_password.text.toString()
            )
            var gson = Gson()
            var jsonUser = gson.toJson(user)
            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(Request.Method.POST, Endpoint.getRegisterEndpoint(),
            JSONObject(jsonUser), Response.Listener {
                Toast.makeText(this, "Register successful", Toast.LENGTH_LONG).show()
                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }

            , Response.ErrorListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                })
            requestQueue.add(request)
        }
    }
}