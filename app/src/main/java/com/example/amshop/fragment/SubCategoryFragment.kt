package com.example.amshop.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.amshop.R
import com.example.amshop.adapter.ProductListRecyclerViewAdapter
import com.example.amshop.app.Endpoint
import com.example.amshop.model.Category
import com.example.amshop.model.Product
import com.example.amshop.model.ProductResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_sub_category.view.*


class SubCategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var subCatId: Int = 0
    private var products = ArrayList<Product>()
    lateinit private var productListAdapter:ProductListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subCatId = it.getInt(Category.CATEGORY_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_sub_category, container, false)
        init(view)
        return view
    }

    private fun init(view: View)
    {
        getData()
        productListAdapter =  ProductListRecyclerViewAdapter(activity!!)
        view.product_list_recycler_view.adapter = productListAdapter
        view.product_list_recycler_view.layoutManager = LinearLayoutManager(activity!!)
    }

    private fun getData()
    {
        var requestQueue = Volley.newRequestQueue(activity)
        var request = StringRequest(Request.Method.GET, Endpoint.getProductEndpoint(subCatId), Response.Listener {
            var gson = Gson()
            var productResponse = gson.fromJson(it, ProductResponse::class.java)
            products = productResponse.data as ArrayList<Product>
            productListAdapter.updateData(products)
        },
        Response.ErrorListener {
            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
        })
        requestQueue.add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            SubCategoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(Category.CATEGORY_KEY, param1)
                }
            }
    }
}