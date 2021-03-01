package com.example.amshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amshop.R
import com.example.amshop.activities.OrderDetailsActivity
import com.example.amshop.model.Order
import com.example.amshop.model.Product
import kotlinx.android.synthetic.main.row_layout_order_list.view.*

class OrdersRecyclerViewAdapter(var context: Context)  : RecyclerView.Adapter<OrdersRecyclerViewAdapter.OrdersViewHolder>(){

    private var orderlist = ArrayList<Order>()

    inner class OrdersViewHolder(var view: View) : RecyclerView.ViewHolder(view)
    {
        fun bind(order : Order)
        {
            view.row_layout_order_list_date.text = order.date.substring(0, order.date.indexOf('T'))
            view.row_layout_order_list_price.text = order.orderSummary.totalAmount.toString()
            view.row_layout_order_list_status.text = order.orderStatus
            view.setOnClickListener {
                var intent = Intent(context, OrderDetailsActivity::class.java)
                intent.putExtra(Order.ORDER_KEY, order )
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        return OrdersViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout_order_list, parent, false))
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.bind(orderlist[position])
    }

    override fun getItemCount(): Int {
       return this.orderlist.size
    }

    fun updateData(list:ArrayList<Order>)
    {
        this.orderlist = list
        notifyDataSetChanged()
    }
}