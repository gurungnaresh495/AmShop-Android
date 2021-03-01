package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.amshop.R
import com.example.amshop.app.Endpoint
import com.example.amshop.helper.SessionManager
import com.example.amshop.model.Address
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_new_address.*
import org.json.JSONObject
import java.lang.reflect.Method

class NewAddressActivity : BaseActivity() {
    override val contentResource: Int = R.layout.activity_new_address
    override var title: String = "Add New Address"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        new_address_submit_button.setOnClickListener {
            var address = Address(null,new_address_city.text.toString(),
                            new_address_house_no.text.toString(),
                            new_address_pincode.text.toString().toInt(),
                            new_address_street_name.text.toString(),
                            new_address_type.text.toString(),
                SessionManager(this).getUserId().toString()
            )

            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(Request.Method.POST,Endpoint.getAddressEndpoint(),
                            JSONObject(Gson().toJson(address)), {
                     startActivity(Intent(this, AddressActivity::class.java))
                },
                {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                }

            )
            requestQueue.add(request)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }
}