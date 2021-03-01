package com.example.amshop.adapter

import android.content.Context
import android.content.Intent
import android.location.Address
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.amshop.R
import com.example.amshop.activities.PaymentActivity
import com.example.amshop.app.Endpoint
import com.example.amshop.model.*
import kotlinx.android.synthetic.main.row_layout_address.view.*

class AddressRecyclerViewAdapter(var context: Context) : RecyclerView.Adapter<AddressRecyclerViewAdapter.MyViewHolder>(){

    private var addressList = ArrayList<com.example.amshop.model.Address>()

    inner class MyViewHolder(var view: View): RecyclerView.ViewHolder(view)
    {
        fun bind(address: com.example.amshop.model.Address)
        {
            view.address_delete_icon.setOnClickListener {
                deleteAddress(address._id!!, adapterPosition)

            }
            view.setOnClickListener {
                var intent = Intent(context, PaymentActivity::class.java)
                intent.putExtra(com.example.amshop.model.Address.ADDRESS_KEY, address)
                context.startActivity(intent)
            }
            view.row_layout_address_street.text = address.houseNo + " " + address.streetName
            view.row_layout_address_city.text = address.city
            view.row_layout_address_pin_code.text = address.pincode.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout_address, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(addressList[position])
    }

    override fun getItemCount(): Int {
        return this.addressList.size
    }

    fun updateAddressList(list: ArrayList<com.example.amshop.model.Address>)
    {
        this.addressList = list
        notifyDataSetChanged()
    }
    fun deleteAddress(addressId: String, position: Int)
    {
        var requestQueue = Volley.newRequestQueue(context)
        var request = StringRequest(Request.Method.DELETE, Endpoint.getAddressEndpoint() + addressId,
            {
                addressList.removeAt(position)
                notifyDataSetChanged()
            },
            {
                Toast.makeText(context, "Failed to delete the address!!", Toast.LENGTH_LONG).show()
            })
        requestQueue.add(request)
    }
}