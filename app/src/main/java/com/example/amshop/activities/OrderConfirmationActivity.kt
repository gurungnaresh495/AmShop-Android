package com.example.amshop.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.amshop.R
import com.example.amshop.app.Endpoint
import com.example.amshop.helper.CartDBHelper
import com.example.amshop.helper.SessionManager
import com.example.amshop.model.Address
import com.example.amshop.model.Order
import com.example.amshop.model.OrderSummary
import com.example.amshop.model.UserXX
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_confirmation.*
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OrderConfirmationActivity : BaseActivity() {
    override var dbHelper = CartDBHelper(this)
    override val contentResource: Int = R.layout.activity_order_confirmation
    override var title: String = "Order Confirmation"
    private lateinit var sessionManager : SessionManager
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        sessionManager = SessionManager(this)
        var requestQueue = Volley.newRequestQueue(this)
        var totalPrice = dbHelper.getPriceTotal()
        var totalDiscount = dbHelper.getDiscountTotal()
        var orderSummary = OrderSummary(null, 0.00, totalDiscount,
         totalPrice -totalDiscount, totalPrice, totalPrice )
        var user = UserXX(sessionManager.getUserId().toString(), sessionManager.getUserEmail().toString(), sessionManager.getUser().toString())
        var order = Order(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()), "Pending", orderSummary,
        dbHelper.getAllProducts(), this.intent.getSerializableExtra(Address.ADDRESS_KEY) as Address,user, user._id )

        var request = JsonObjectRequest(Request.Method.POST, Endpoint.getOrdersEndpoint(),
                    JSONObject(Gson().toJson(order)), {
                        order_confirmation_progressbar.visibility = View.GONE
                        confirmed_view.visibility=View.VISIBLE
                dbHelper.emptyCart()
        },
        {
            order_confirmation_thank_you.text ="Failed"
        })

        requestQueue.add(request)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }
}