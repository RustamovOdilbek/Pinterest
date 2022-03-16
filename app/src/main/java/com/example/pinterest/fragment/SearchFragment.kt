package com.example.pinterest.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest.R
import com.example.pinterest.adapter.ImageAdapter
import com.example.pinterest.newtworking.ApiClient
import com.example.pinterst.model.ImageModel
import com.example.pinterst.model.SearchModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import com.example.pinterest.fragment.Home.ProfileActivity

class SearchFragment : Fragment() {
    lateinit var root: View
    lateinit var recyclerView: RecyclerView
    lateinit var et_search: EditText
    lateinit var list: ArrayList<ImageModel>
    lateinit var adapter: ImageAdapter
    private var per_page = 100
    lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_search, container, false)

        initViews()

        adapter.imageClick = {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            intent.putExtra("image", it)
            startActivity(intent)
        }

        return root
    }

    private fun initViews() {
        recyclerView = root.findViewById(R.id.recyclerView)
        et_search = root.findViewById(R.id.et_search)

        et_search.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchData(et_search.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        list = ArrayList()
       adapter = ImageAdapter(root.context, list)
        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)


    }


    private fun searchData(query: String) {
        ApiClient.getApiInterface().searchImages(query,  per_page)
            .enqueue(object : Callback<SearchModel> {
                override fun onResponse(call: Call<SearchModel>, response: Response<SearchModel>) {
                    list.clear()
                    list.addAll(response.body()!!.results)
                    recyclerView.layoutManager = staggeredGridLayoutManager
                    recyclerView.adapter = adapter
                }

                override fun onFailure(call: Call<SearchModel>, t: Throwable) {

                }

            })
    }
}