package com.example.amshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.amshop.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        init()
    }

    private fun init()
    {
        var thread = Thread()
        {
            kotlin.run{
                Thread.sleep(3000)
            }
            var intent = Intent(this, CategoryListActivity::class.java)
            startActivity(intent)
            finish()
        }.start()
    }
}