package com.example.pinterest.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.pinterest.R
import com.example.pinterest.holder.SharePref
import com.example.pinterest.utils.DeepLink

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

//        throw RuntimeException("Test Crash")

        //DeepLink.createLongLink("123456789")
        DeepLink.createShortLink("654321")

        val handler =  Handler()
        handler.postDelayed({
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                }
        }, 10000)
    }
}