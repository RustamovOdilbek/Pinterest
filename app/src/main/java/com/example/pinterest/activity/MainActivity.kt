package com.example.pinterest.activity

import android.Manifest
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.pinterest.R
import com.example.pinterest.fragment.Home.HomeFragment
import com.example.pinterest.fragment.message.MessageFragment
import com.example.pinterest.fragment.AccountFragment
import com.example.pinterest.fragment.SearchFragment
import com.example.pinterest.utils.DeepLink
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var fragmentHome: HomeFragment
    lateinit var fragmentSearch: SearchFragment
    lateinit var fragmentMessage: MessageFragment
    lateinit var profileFragment: AccountFragment


    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        val tv_link = findViewById<TextView>(R.id.tv_link)
        DeepLink.retrieveLink(intent, tv_link)


        initViews()
    }

    fun initViews(){
        fragmentHome = HomeFragment()
        fragmentSearch = SearchFragment()
        fragmentMessage = MessageFragment()
        profileFragment = AccountFragment()
        bottomNavigationView = findViewById(R.id.bottom_nav_menu)

        bottomNavigationView.menu.getItem(0).isChecked = true
        loadFragment(fragmentHome)

        bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK))

        bottomNavigationView.setOnNavigationItemSelectedListener {menu ->
            when(menu.itemId){
                R.id.iv_home -> {
                    loadFragment(fragmentHome)
                    true
                }
                R.id.iv_search -> {
                    loadFragment(fragmentSearch)
                    true
                }
                R.id.iv_message -> {
                    loadFragment(fragmentMessage)
                    true
                }
                R.id.iv_person -> {
                    loadFragment(profileFragment)
                    true
                }
                else -> false
            }
        }
    }
    fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fm_home, fragment).commit()
    }
}