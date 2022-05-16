package com.example.pinterest.fragment.Home

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest.holder.EndlessRecyclerViewScrollListener
import com.example.pinterest.R
import com.example.pinterest.adapter.ImageAdapter
import com.example.pinterest.newtworking.ApiClient
import com.example.pinterst.model.ImageModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var list: ArrayList<ImageModel>
    lateinit var adapter: ImageAdapter
    private lateinit var dialog: ProgressDialog
    private var isLoading: Boolean? = null
    private var isLastPage: Boolean? = null
    lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        root = inflater.inflate(R.layout.fragment_home, container, false)

        initViews()

        return root
    }

    private fun initViews() {
       recyclerView = root.findViewById(R.id.recyclerView)
       list = ArrayList()

       adapter = ImageAdapter(root.context, list)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = adapter

        adapter.imageClick = {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            intent.putExtra("image", it)
            startActivity(intent)
        }

        dialog = ProgressDialog(root.context)
        dialog.setMessage("Loading...")
        dialog.setCancelable(false)
        dialog.show()

        apiImageList(1)


        val scrollListener = object : EndlessRecyclerViewScrollListener(staggeredGridLayoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                apiImageList(page+1)
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun apiImageList(page: Int) {
        ApiClient.getApiInterface().getImages(page)
            .enqueue(object : Callback<List<ImageModel>>{
            override fun onResponse(
                call: Call<List<ImageModel>>,
                response: Response<List<ImageModel>>
            ) {
                list.addAll(response.body() as ArrayList<ImageModel>)
                adapter.notifyDataSetChanged()

                isLoading = false
                dialog.dismiss()
                if (list.size > 0){
                    isLastPage = list.size < 10
                }else isLastPage = true
            }

            override fun onFailure(call: Call<List<ImageModel>>, t: Throwable) {
                dialog.dismiss()
                Toast.makeText(root.context, "Error", Toast.LENGTH_SHORT).show()
            }

        })
    }
}