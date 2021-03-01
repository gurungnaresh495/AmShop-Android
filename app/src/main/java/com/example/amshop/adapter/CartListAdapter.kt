package com.example.amshop.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amshop.R
import com.example.amshop.activities.CartActivity
import com.example.amshop.app.Config
import com.example.amshop.helper.CartDBHelper
import com.example.amshop.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_list_row.view.*
import kotlinx.android.synthetic.main.increase_text_decrease.view.*
import kotlinx.android.synthetic.main.product_list_row_layout.view.*

class CartListAdapter(var context: Context,var list: ArrayList<Product>): RecyclerView.Adapter<CartListAdapter.CartListViewHolder>() {

    var subtotal = 0.00
    var discount  = 0.00
    var dbHelper = CartDBHelper(context)
    var listener: CartListButtonListener = context as CartActivity

    inner class CartListViewHolder(var view: View) : RecyclerView.ViewHolder(view)
    {
        fun bind(product: Product)
        {
            view.row_layout_text_view_name.text = product.productName
            view.row_layout_text_view_mrp.text = "$" +product.mrp.toString()
            view.row_layout_text_view_mrp.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            view.row_layout_text_view_price.text = "$" +product.price.toString()
            view.product_count.text = dbHelper.getQuantityOfProduct(product).toString()
            view.row_layout_text_view_discount.text = String.format("Discount: %.2f",(product.mrp -product.price))
            Picasso.get().load(Config.IMAGE_URL + product.image).into(view.cart_list_row_image_view)

            view.increase_button.setOnClickListener{
                dbHelper.increaseProduct(product)
                view.product_count.text = dbHelper.getQuantityOfProduct(product).toString()
                listener.onClickButtonCarList()
            }

            view.decrease_button.setOnClickListener{
                dbHelper.decreaseProduct(product)
                if (dbHelper.getQuantityOfProduct(product) == 0)
                {
                    list.removeAt(adapterPosition)
                    dbHelper.deleteProduct(product)
                    listener.onClickButtonCarList()
                    notifyDataSetChanged()
                }
                else
                    view.product_count.text = dbHelper.getQuantityOfProduct(product).toString()
                listener.onClickButtonCarList()
            }

            view.cart_delete_icon.setOnClickListener {
                list.removeAt(adapterPosition)
                dbHelper.deleteProduct(product)
                listener.onClickButtonCarList()
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        return CartListViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_list_row, parent, false))
    }

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
       holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun calculateSubtotal()
    {
        subtotal = 0.0
        discount = 0.0
        for (i in list)
        {
            subtotal += (i.mrp * dbHelper.getQuantityOfProduct(i))
            discount += ((i.mrp - i.price) * dbHelper.getQuantityOfProduct(i))
        }
    }

    interface CartListButtonListener
    {
        fun onClickButtonCarList()
    }

}