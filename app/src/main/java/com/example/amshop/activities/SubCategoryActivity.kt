package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.amshop.R
import com.example.amshop.adapter.MyTabLayoutAdapter
import com.example.amshop.adapter.ProductListRecyclerViewAdapter
import com.example.amshop.app.Endpoint
import com.example.amshop.model.Category
import com.example.amshop.model.SubCategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_category_list.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.top_nav_bar.*

class SubCategoryActivity() : BaseActivity(){

    override lateinit var title: String
    override var contentResource = R.layout.activity_sub_category
    lateinit var myTabLayout: MyTabLayoutAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        var category = this.intent.getSerializableExtra(Category.CATEGORY_KEY) as Category
        title = category.catName
        super.onCreate(savedInstanceState)
        init(category)
    }

    private fun init(category: Category) {
        getData(category.catId)
        myTabLayout = MyTabLayoutAdapter(supportFragmentManager)
        sub_category_view_pager.adapter = myTabLayout
        sub_category_tab_layout.setupWithViewPager(sub_category_view_pager)


    }


    private fun getData(catId: Int) {

        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(Request.Method.GET, Endpoint.getSubCategoryEndpoint(catId), {
            var gson = Gson()
            var subCategoryResponse = gson.fromJson(it, SubCategoryResponse::class.java)
            for (i in subCategoryResponse.data) {
                myTabLayout.addFragment(i)
            }
        },
            {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            })
        requestQueue.add(request)
    }
}