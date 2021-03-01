package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import com.example.amshop.R
import com.example.amshop.model.Address
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity() : BaseActivity() {
    override val contentResource = R.layout.activity_payment
    override var title: String = "Select Your Payment Type"
    lateinit var method: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {

        payment_radio_group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId)
            {
                R.id.payment_selection_cash -> method = "cash"
                R.id.payment_selection_card -> method = "card"
            }
            pay_now_button.visibility = View.VISIBLE
        }
        pay_now_button.setOnClickListener {

            if (payment_selection_card.isChecked)
            {
                method = "Card"
            }
            else
                method = "Cash"
            var pay_intent = Intent(this, OrderConfirmationActivity::class.java)
            pay_intent.putExtra(Address.ADDRESS_KEY, this.intent.getSerializableExtra(Address.ADDRESS_KEY))
            pay_intent.putExtra(PAYMENT_KEY, method)
            startActivity(pay_intent)
        }
    }

    companion object
    {
        const val PAYMENT_KEY = "Payment"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }
}