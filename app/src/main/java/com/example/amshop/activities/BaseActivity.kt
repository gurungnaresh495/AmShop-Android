package com.example.amshop.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import com.example.amshop.R
import com.example.amshop.adapter.ProductListRecyclerViewAdapter
import com.example.amshop.helper.CartDBHelper
import com.example.amshop.helper.SessionManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_base.view.*
import kotlinx.android.synthetic.main.cart_layout.view.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlinx.android.synthetic.main.top_nav_bar.*

abstract class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ProductListRecyclerViewAdapter.ProductListOnClickListener {
    lateinit var drawerLayout: DrawerLayout
    open lateinit var dbHelper: CartDBHelper
    private var textViewCartCount: TextView? = null
    abstract val contentResource : Int
    abstract var title: String
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        init()
    }
    private fun init()
    {
        dbHelper = CartDBHelper(this)
        sessionManager = SessionManager(this)

        var view = LayoutInflater.from(this).inflate(contentResource, drawer_layout, false)
        var bar = LayoutInflater.from(this).inflate(R.layout.top_nav_bar, drawer_layout, false)

        drawerLayout = drawer_layout
        drawerLayout.addView(view, 0)
        var navView = nav_view
        if (view is LinearLayout)
        {
            view.addView(bar, 0)
        }

        navView.setNavigationItemSelectedListener (this)


        var toolbar = nav_bar
        toolbar.title = this.title
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)

        var toggle = ActionBarDrawerToggle(this, drawerLayout, nav_bar,0,0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        getUserData()
    }


    private fun getUserData()
    {
        var headerView = nav_view.getHeaderView(0)

        if (sessionManager.isLoggedIn())
        {
            var navMenu = nav_view.menu
            navMenu.findItem(R.id.item_login).isVisible = false
            headerView.header_text_view_email.text = sessionManager.getUserEmail()
            headerView.header_text_view_name.text = sessionManager.getUser()
        }
        else
        {
            var navMenu = nav_view.menu
            navMenu.findItem(R.id.item_logout).isVisible = false
            navMenu.findItem(R.id.item_account).isVisible = false
            navMenu.findItem(R.id.item_order).isVisible = false
            navMenu.findItem(R.id.item_billing).isVisible = false
            navMenu.findItem(R.id.item_refer).isVisible = false
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.main_menu), menu)
        var item = menu?.findItem(R.id.cart)
        MenuItemCompat.setActionView(item, R.layout.cart_layout)
        var view = MenuItemCompat.getActionView(item)
        textViewCartCount = view.cart_icon_text_view
        view.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }

        var count = dbHelper.getNumberOfProducts()
        if (count == 0)
        {
            textViewCartCount?.visibility = View.GONE
        }
        else
        {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_account -> startActivity(Intent(this, ProfileActivity::class.java))
            R.id.item_billing -> Toast.makeText(this, "Billing", Toast.LENGTH_LONG).show()
            R.id.item_help -> Toast.makeText(this, "Help", Toast.LENGTH_LONG).show()
            R.id.item_order -> startActivity(Intent(this, OrderHistoryActivity::class.java))
            R.id.item_rate_app -> Toast.makeText(this, "Rate", Toast.LENGTH_LONG).show()
            R.id.item_refer -> Toast.makeText(this, "Refer", Toast.LENGTH_LONG).show()
            R.id.item_login -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.item_logout -> {
                sessionManager.logout()
                dbHelper.emptyCart()
                startActivity(Intent(this, LoginActivity::class.java))
            }

        }
        return true
    }

    override fun onClick() {
       updateCartCount()
    }

    protected fun updateCartCount()
    {
        var count = dbHelper.getNumberOfProducts()
        textViewCartCount?.visibility = View.GONE
        if (count == 0)
        {
            textViewCartCount?.visibility = View.GONE
        }
        else
        {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if(textViewCartCount != null)
        {
            updateCartCount()
        }

    }

}