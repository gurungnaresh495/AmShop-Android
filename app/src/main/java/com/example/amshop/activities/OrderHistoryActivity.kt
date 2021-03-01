package com.example.amshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.amshop.R
import com.example.amshop.adapter.OrdersRecyclerViewAdapter
import com.example.amshop.app.Endpoint
import com.example.amshop.helper.SessionManager
import com.example.amshop.model.Order
import com.example.amshop.model.OrderResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_history.*
import java.lang.reflect.Method

class OrderHistoryActivity : BaseActivity() {
    private var orderList = ArrayList<Order>()
    private lateinit var orderListAdapter: OrdersRecyclerViewAdapter
    override val contentResource: Int = R.layout.activity_order_history

    override var title: String = "Order History"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        getData()
        orderListAdapter= OrdersRecyclerViewAdapter(this)
        recycler_view_orders.adapter = orderListAdapter
        recycler_view_orders.layoutManager =LinearLayoutManager(this)
    }

    private fun getData()
    {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(Request.Method.GET, Endpoint.getOrdersEndpoint()+ SessionManager(this).getUserId(), {
            orderList = Gson().fromJson(it, OrderResponse::class.java).data
            orderListAdapter.updateData(orderList)
        },
            {
                Log.d("abc", it.message.toString())
            })

        requestQueue.add(request)
    }
}