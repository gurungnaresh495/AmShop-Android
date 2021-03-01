package com.example.amshop.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amshop.R
import com.example.amshop.activities.SubCategoryActivity
import com.example.amshop.app.Config
import com.example.amshop.model.Category
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoProvider
import kotlinx.android.synthetic.main.row_layout.view.*

class MyRecyclerViewAdaptar(var context: Context ) : RecyclerView.Adapter<MyRecyclerViewAdaptar.MyViewHolder>(){

    var list = ArrayList<Category>()

    inner class MyViewHolder(var view: View): RecyclerView.ViewHolder(view)
    {
        fun bind(category: Category)
        {
            Picasso.get().load(Config.IMAGE_URL + category.catImage).into(view.row_layout_image_view)
            view.text_view_category_name.text = category.catName
            view.setOnClickListener {
                var intent = Intent(context, SubCategoryActivity::class.java)
                intent.putExtra(Category.CATEGORY_KEY, category)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(context).inflate( R.layout.row_layout, parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var category = list[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(catList: ArrayList<Category>)
    {
        this.list = catList
        notifyDataSetChanged()
    }
}