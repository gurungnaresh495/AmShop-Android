package com.example.amshop.activities

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.amshop.R
import com.example.amshop.adapter.MyRecyclerViewAdaptar
import com.example.amshop.app.Config
import com.example.amshop.app.Endpoint
import com.example.amshop.helper.CartDBHelper
import com.example.amshop.helper.SessionManager
import com.example.amshop.model.Category
import com.example.amshop.model.CategoryResponse
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_category_list.*
import kotlinx.android.synthetic.main.cart_layout.view.*
import kotlinx.android.synthetic.main.category_activity_content.*
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlinx.android.synthetic.main.top_nav_bar.*

class CategoryListActivity() : BaseActivity() , NavigationView.OnNavigationItemSelectedListener {
    override val contentResource: Int = R.layout.category_activity_content
    override var title: String = "AmShop"
    lateinit var myListAdapter: MyRecyclerViewAdaptar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        getCategories()
        myListAdapter = MyRecyclerViewAdaptar(this)
        category_recycler_view.adapter = myListAdapter
        category_recycler_view.layoutManager = GridLayoutManager(this, 2)
    }

    private fun getCategories() {
        var requestQueue = Volley.newRequestQueue(this)

        var request =
            StringRequest(Request.Method.GET, Endpoint.getCategoryEndpoint(), {
                var gson = Gson()
                var categoryResponse = gson.fromJson(it, CategoryResponse::class.java)
                myListAdapter.updateData(categoryResponse.data as ArrayList<Category>)

            },
                {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                })
        requestQueue.add(request)
    }


}