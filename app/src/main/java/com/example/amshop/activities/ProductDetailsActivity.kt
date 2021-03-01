package com.example.amshop.activities

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.amshop.R
import com.example.amshop.helper.CartDBHelper
import com.example.amshop.model.Category
import com.example.amshop.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.cart_list_row.view.*
import kotlinx.android.synthetic.main.increase_text_decrease.*
import kotlinx.android.synthetic.main.top_nav_bar.*
import kotlin.properties.Delegates


class ProductDetailsActivity() : BaseActivity() {
    override var dbHelper = CartDBHelper(this)
    override var contentResource = R.layout.activity_product_details
    override lateinit var title: String
    override fun onCreate(savedInstanceState: Bundle?) {
        var product = intent.getSerializableExtra(Product.PRODUCT_KEY) as Product
        title = product.productName
        super.onCreate(savedInstanceState)
        init(product)
    }

    private fun init(product: Product)
    {

        details_activity_product_name.text = product.productName
        details_activity_product_price.text = "$" + product.price.toString()
        details_activity_product_mrp.text = "$" + product.mrp.toString()
        details_activity_product_mrp.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        product_details_discount_percent.text = ((product.mrp -product.price) / product.mrp * 100).toInt().toString() + "%\noff"
        details_activity_product_description.movementMethod = ScrollingMovementMethod()
        details_activity_product_description.text = product.description
        Picasso.get().load("https://rjtmobile.com/grocery/images/${product.image}").into(details_activity_image_view)

        if (dbHelper.getQuantityOfProduct(product)> 0)
        {
            product_details_add_to_cart_text_views.visibility = View.VISIBLE
            details_activity_add_to_cart_button.visibility = View.GONE
        }
        else
        {
            product_details_add_to_cart_text_views.visibility = View.GONE
        }

        product_count.text = dbHelper.getQuantityOfProduct(product).toString()

        details_activity_add_to_cart_button.setOnClickListener{
            dbHelper.addProduct(product)
            product_details_add_to_cart_text_views.visibility = View.VISIBLE
            details_activity_add_to_cart_button.visibility = View.GONE
            product_count.text = dbHelper.getQuantityOfProduct(product).toString()
            super.updateCartCount()
        }

        increase_button.setOnClickListener{
            dbHelper.increaseProduct(product)
            product_count.text = dbHelper.getQuantityOfProduct(product).toString()
            super.updateCartCount()
        }

        decrease_button.setOnClickListener{
            dbHelper.decreaseProduct(product)
            product_count.text = dbHelper.getQuantityOfProduct(product).toString()
            if (CartDBHelper(this).getQuantityOfProduct(product)== 0)
            {
                product_details_add_to_cart_text_views.visibility = View.GONE
                details_activity_add_to_cart_button.visibility = View.VISIBLE
            }
            super.updateCartCount()
        }



    }


}