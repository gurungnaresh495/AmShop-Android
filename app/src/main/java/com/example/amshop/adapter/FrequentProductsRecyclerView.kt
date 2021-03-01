package com.example.amshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amshop.R
import com.example.amshop.activities.ProductDetailsActivity
import com.example.amshop.app.Config
import com.example.amshop.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout_frequent_products.view.*

class FrequentProductsRecyclerView(var context: Context) :RecyclerView.Adapter<FrequentProductsRecyclerView.FrequentProductViewHolder>(){

    private var productList = ArrayList<Product>()
    inner class FrequentProductViewHolder(var view : View) : RecyclerView.ViewHolder(view)
    {
        fun bind(product: Product)
        {
            view.setOnClickListener {
                var intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra(Product.PRODUCT_KEY, product)
                context.startActivity(intent)
            }
            view.row_layout_frequent_products_name.text = product.productName
            Picasso.get().load(Config.IMAGE_URL + product.image).into(view.row_layout_frequent_products_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrequentProductViewHolder {
        return FrequentProductViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout_frequent_products, parent, false))
    }

    override fun onBindViewHolder(holder: FrequentProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return this.productList.size
    }

    fun update(list: ArrayList<Product>)
    {
        this.productList = list
        notifyDataSetChanged()
    }
}