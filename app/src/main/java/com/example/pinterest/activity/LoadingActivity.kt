package com.example.pinterest.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.pinterest.R
import com.example.pinterest.holder.SharePref

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

//        throw RuntimeException("Test Crash")

        val handler =  Handler()
        handler.postDelayed({
            if (SharePref(this).isSaved == true){
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                }
            }else {
                Intent(this, RegisterActivity::class.java).also {
                    startActivity(it)
                    SharePref(this).isSaved = true
                }
            }
        }, 10000)
    }
}