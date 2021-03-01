package com.example.amshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amshop.R
import com.example.amshop.app.Config
import com.example.amshop.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_list_row.view.*
import kotlinx.android.synthetic.main.row_layout_order_details.view.*

class OrderDetailsRecyclerViewAdapter(var context: Context, private var productList: ArrayList<Product>) : RecyclerView.Adapter<OrderDetailsRecyclerViewAdapter.OrderDetailsViewHolder>(){

    inner class OrderDetailsViewHolder(var view: View): RecyclerView.ViewHolder(view)
    {
        fun bind(product: Product)
        {
            view.order_details_row_text_view_name.text = product.productName
            view.order_details_row_price.text = product.price.toString()
            Picasso.get().load(Config.IMAGE_URL + product.image).into(view.order_details_row_image_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        return OrderDetailsViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout_order_details, parent ,false))
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return this.productList.size
    }
}