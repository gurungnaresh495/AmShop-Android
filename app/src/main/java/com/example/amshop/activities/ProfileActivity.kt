package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.amshop.R
import com.example.amshop.adapter.FrequentProductsRecyclerView
import com.example.amshop.app.Config
import com.example.amshop.app.Endpoint
import com.example.amshop.helper.SessionManager
import com.example.amshop.model.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.properties.Delegates

class ProfileActivity : BaseActivity() {

    private var orderlist = ArrayList<Order>()
    private var listOfFrequentProductsId = ArrayList<String>()
    private var listOfFrequentProducts = ArrayList<Product>()
    private lateinit var userId :String
    private lateinit var listAdapter : FrequentProductsRecyclerView
    private lateinit var user : UserX
    override val contentResource: Int = R.layout.activity_profile
    override var title: String = "Profile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        listAdapter = FrequentProductsRecyclerView(this)
        recycler_view_profile.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recycler_view_profile.adapter = listAdapter
        userId = SessionManager(this).getUserId().toString()
        getOrders()
        getUser()
        getAddresses()

        user_profile_edit_profile_button.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
    }

    private fun getOrders() {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET, Endpoint.getOrdersEndpoint() + SessionManager(this).getUserId(), {
                orderlist = Gson().fromJson(it, OrderResponse::class.java).data
                user_profile_order_count.text = orderlist.size.toString()
                getFrequentItems()
                populateListOfFrequentProducts()
            },
            {
                Log.d("abc", it.message.toString())
            })

        requestQueue.add(request)
    }

    private fun getProductsTotalQuantities(): HashMap<String, Int> {
        var map = HashMap<String, Int>()

        for (i in orderlist) {
            for (j in i.products) {
                if (j._id in map)
                    map[j._id] = map[j._id]!! + (j.quantity)
                else
                    map[j._id] = j.quantity
            }
        }
        return map

    }

    private fun getFrequentItems() {
        var map = getProductsTotalQuantities()
        var currentMaxValue: Int
        while ((listOfFrequentProducts.size <= Config.FREQUENT_BOUGHT_NUMBER) && !map.isNullOrEmpty()) {
            currentMaxValue = Collections.max(map.values)
            listOfFrequentProductsId.addAll(map.filterValues { it == currentMaxValue }.keys.toList())
            while (map.values.remove(currentMaxValue))
            {
            }
        }

    }

    private fun populateListOfFrequentProducts() {
        var requestQueue = Volley.newRequestQueue(this)
        for (i in listOfFrequentProductsId) {

            var request = StringRequest(
                Request.Method.GET, Endpoint.getSingleProductEndpoint(i), {
                    var product = Gson().fromJson(it, SingleProductResponse::class.java).data
                    listOfFrequentProducts.add(product)
                    listAdapter.update(listOfFrequentProducts)
                },
                {
                    Log.d("abc", it.message.toString())
                })

            requestQueue.add(request)
        }
    }

    private fun getUser()
    {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET, Endpoint.getUsersEndpoint() + userId, {
                user = Gson().fromJson(it, UserResponse::class.java).data
                user_profile_user_name.text = user.firstName
                user_profile_user_email.text = user.email
                user_profile_user_phone.text = user.mobile
                user_profile_user_member_since.text = user.createdAt.substring(0, user.createdAt.indexOf('T'))
            },
            {
                Log.d("abc", it.message.toString())
            })

        requestQueue.add(request)
    }

    private fun getAddresses()
    {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET, Endpoint.getAddressEndpoint() + userId, {
                var addressCount = Gson().fromJson(it, UserAddressResponse::class.java).count
                user_profile_address_count.text = addressCount.toString()
            },
            {
                Log.d("abc", it.message.toString())
            })

        requestQueue.add(request)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }

}