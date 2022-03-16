package com.example.pinterest.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest.R
import com.example.pinterest.adapter.ImageAdapter
import com.example.pinterest.adapter.SaveAdapter
import com.example.pinterest.fragment.Home.ProfileActivity
import com.example.pinterest.model.home.save.SaveId
import com.example.pinterest.newtworking.ApiClient
import com.example.pinterest.newtworking.database.SaveRepository
import com.example.pinterst.model.ImageModel
import com.example.pinterst.model.UrlModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountFragment : Fragment() {
    lateinit var saveImage: ArrayList<SaveId>
    lateinit var recyclerView: RecyclerView
    lateinit var root: View
    lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    lateinit var adapter: SaveAdapter
    lateinit var saveUrl: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_account, container, false)
        saveImage = ArrayList()
        saveUrl = ArrayList()
        recyclerView = root.findViewById(R.id.recyclerView)

        var repository = SaveRepository(requireContext())
        saveImage = repository.getUsers() as ArrayList<SaveId>

        apiImage()

        adapter.imageClick = {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            intent.putExtra("image", it)
            startActivity(intent)
        }

        return root
    }
    private fun apiImage() {
        for (i in 0..saveImage.size - 1) {
            saveUrl.add(saveImage[i].regular)
        }

        Log.d("#########3", saveUrl.toString())

        adapter = SaveAdapter(requireContext(), saveImage)
        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = adapter

    }

}