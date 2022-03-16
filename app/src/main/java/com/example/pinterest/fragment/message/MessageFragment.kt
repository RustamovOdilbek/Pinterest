package com.example.pinterest.fragment.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.pinterest.R

class MessageFragment : Fragment() {
    private lateinit var root: View
    private lateinit var tv_update: TextView
    private lateinit var tv_unwanted: TextView
    private lateinit var iv_update: ImageView
    private lateinit var iv_unwanted: ImageView
    private lateinit var frameLayout: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_message, container, false)
        tv_unwanted = root.findViewById(R.id.tv_unwanted)
        tv_update = root.findViewById(R.id.tv_update)
        iv_unwanted = root.findViewById(R.id.iv_unwanted)
        iv_update = root.findViewById(R.id.iv_update)
        frameLayout = root.findViewById(R.id.frameLayout)

        childFragmentManager.beginTransaction().replace(R.id.frameLayout, UpdatesFragment()).commit()

        initViews()

        return root
    }

    private fun initViews() {

        tv_unwanted.setOnClickListener {
            iv_update.visibility = GONE
            iv_unwanted.visibility = VISIBLE
            childFragmentManager.beginTransaction().replace(R.id.frameLayout, UnwantedFragment()).commit()
        }
        tv_update.setOnClickListener {
            iv_update.visibility = VISIBLE
            iv_unwanted.visibility = GONE
            childFragmentManager.beginTransaction().replace(R.id.frameLayout, UpdatesFragment()).commit()
        }
    }
}