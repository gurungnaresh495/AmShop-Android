package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.amshop.R
import com.example.amshop.adapter.AddressRecyclerViewAdapter
import com.example.amshop.app.Endpoint
import com.example.amshop.helper.CartDBHelper
import com.example.amshop.helper.SessionManager
import com.example.amshop.model.Address
import com.example.amshop.model.UserAddressResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_address.*
import kotlin.properties.Delegates

class AddressActivity: BaseActivity() {

    private lateinit var listOfAddress: ArrayList<Address>
    lateinit var listAdapter: AddressRecyclerViewAdapter
    override val contentResource = R.layout.activity_address

    override var title = "Select Your Address"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onRestart() {
        super.onRestart()
        getAllAddress()
        listAdapter.updateAddressList(listOfAddress)
    }

    private fun init()
    {
        getAllAddress()
        listAdapter = AddressRecyclerViewAdapter(this)
        address_recycler_view.adapter = listAdapter
        address_recycler_view.layoutManager =LinearLayoutManager(this)
        add_new_address_button.setOnClickListener {
            startActivity(Intent(this, NewAddressActivity::class.java))
        }
    }

    private fun getAllAddress()
    {
        var userId = SessionManager(this).getUserId()
        var requestQueue =  Volley.newRequestQueue(this)
        var request = StringRequest(Request.Method.GET, Endpoint.getAddressEndpoint() + userId, {
            var gson = Gson()
            listOfAddress = gson.fromJson(it, UserAddressResponse::class.java).data as ArrayList<Address>
            listAdapter.updateAddressList(listOfAddress)
        },
            {

            })
        requestQueue.add(request)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }

}