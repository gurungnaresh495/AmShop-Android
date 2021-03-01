package com.example.amshop.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amshop.R
import com.example.amshop.activities.BaseActivity
import com.example.amshop.activities.ProductDetailsActivity
import com.example.amshop.activities.SubCategoryActivity
import com.example.amshop.app.Config
import com.example.amshop.helper.CartDBHelper
import com.example.amshop.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.cart_list_row.view.*
import kotlinx.android.synthetic.main.increase_text_decrease.*
import kotlinx.android.synthetic.main.increase_text_decrease.view.*
import kotlinx.android.synthetic.main.product_list_row_layout.view.*

class ProductListRecyclerViewAdapter(var context: Context) : RecyclerView.Adapter<ProductListRecyclerViewAdapter.ProductListViewHolder>() {
    private var productList = ArrayList<Product>()
    var dbHelper = CartDBHelper(context)
    var listener: BaseActivity = context as BaseActivity
    inner class ProductListViewHolder(var view: View): RecyclerView.ViewHolder(view)
    {
        fun bind(product: Product)
        {
            view.setOnClickListener {
                var intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra(Product.PRODUCT_KEY, product)
                context.startActivity(intent)
            }

            view.product_list_row_layout_text_view_product_name.text = product.productName
            view.product_list_row_layout_text_view_price.text = "$" + product.price.toString()

            Picasso.get().load(Config.IMAGE_URL + product.image).into(view.product_list_row_layout_image_view)

            if (dbHelper.getQuantityOfProduct(product)> 0)
            {
                view.product_details_add_to_cart_text_views.visibility = View.VISIBLE
                view.details_activity_add_to_cart_button.visibility = View.GONE
            }
            else
            {
                view.product_details_add_to_cart_text_views.visibility = View.GONE
            }

            view.product_count.text = dbHelper.getQuantityOfProduct(product).toString()

            view.details_activity_add_to_cart_button.setOnClickListener{
                dbHelper.addProduct(product)
                view.product_details_add_to_cart_text_views.visibility = View.VISIBLE
                view.details_activity_add_to_cart_button.visibility = View.GONE
                view.product_count.text = dbHelper.getQuantityOfProduct(product).toString()
                listener.onClick()
            }

            view.increase_button.setOnClickListener{
                dbHelper.increaseProduct(product)
                view.product_count.text = dbHelper.getQuantityOfProduct(product).toString()
                listener.onClick()
            }

            view.decrease_button.setOnClickListener{
                dbHelper.decreaseProduct(product)
                view.product_count.text = dbHelper.getQuantityOfProduct(product).toString()
                if (dbHelper.getQuantityOfProduct(product)== 0)
                {
                    view.product_details_add_to_cart_text_views.visibility = View.GONE
                    view.details_activity_add_to_cart_button.visibility = View.VISIBLE
                }
                listener.onClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder(LayoutInflater.from(context).inflate(R.layout.product_list_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    interface ProductListOnClickListener
    {
        fun onClick()
    }

    fun updateData(list: ArrayList<Product>)
    {
        this.productList = list
        notifyDataSetChanged()
    }
}