package com.example.amshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amshop.R
import com.example.amshop.adapter.OrderDetailsRecyclerViewAdapter
import com.example.amshop.model.Order
import com.example.amshop.model.Product
import kotlinx.android.synthetic.main.activity_order_details.*

class OrderDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        init()
    }

    private fun init() {
        var order = this.intent.getSerializableExtra(Order.ORDER_KEY) as Order
        order_details_discount.text = order.orderSummary.deliveryCharges.toString()
        order_details_discount.text = order.orderSummary.discount.toString()
        order_details_sub_total.text = order.orderSummary.orderAmount.toString()
        order_details_total.text =order.orderSummary.totalAmount.toString()

        val listAdapter = OrderDetailsRecyclerViewAdapter(this,
            order.products as ArrayList<Product>
        )

        order_details_recycler_view.adapter = listAdapter
        order_details_recycler_view.layoutManager = LinearLayoutManager(this)
    }
}