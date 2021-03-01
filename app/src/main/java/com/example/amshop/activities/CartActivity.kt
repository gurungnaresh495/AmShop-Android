package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amshop.R
import com.example.amshop.adapter.CartListAdapter
import com.example.amshop.helper.CartDBHelper
import com.example.amshop.helper.SessionManager
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity() :BaseActivity(), CartListAdapter.CartListButtonListener {
    override val contentResource = R.layout.activity_cart
    override var title: String = "Cart"
    lateinit var cartListAdapter: CartListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        var sessionManager = SessionManager(this)
        cartListAdapter = CartListAdapter(this, CartDBHelper(this).getAllProducts())
        cart_list_recyler_view.adapter = cartListAdapter
        cart_list_recyler_view.layoutManager = LinearLayoutManager(this)
        updateSummary()
        cart_checkout_button.setOnClickListener{
            if (!sessionManager.isLoggedIn()){
                startActivity(Intent(this, LoginActivity::class.java))
            }
            else
                startActivity(Intent(this, AddressActivity::class.java))
        }

    }
    private fun updateSummary()
    {
        cartListAdapter.calculateSubtotal()
        cart_text_viewsub_total.text = cartListAdapter.subtotal.toString()
        cart_text_view_discount.text =String.format("%.2f", cartListAdapter.discount)
        cart_text_views_delivery_charges.text = (0.00).toString()
        cart_text_view_total.text = (cartListAdapter.subtotal - cartListAdapter.discount).toString()
    }

    override fun onClickButtonCarList() {
        updateSummary()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }
}