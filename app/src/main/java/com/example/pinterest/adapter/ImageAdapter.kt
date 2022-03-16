package com.example.pinterest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest.R
import com.example.pinterest.model.home.save.SaveId
import com.example.pinterest.newtworking.database.SaveRepository
import com.example.pinterst.model.ImageModel

class ImageAdapter(var context: Context, var items: ArrayList<ImageModel>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var imageClick: ((String) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_picture, parent, false)
        return PhotoViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = items[position]
        if (holder is PhotoViewHolder){
            val iv_image = holder.iv_image
            val iv_more = holder.iv_more
            val tv_title = holder.tv_title
            val tv_like = holder.tv_like
            val ll_home = holder.ll_home

            Glide.with(iv_image.context).load(item.urls.small).placeholder(ColorDrawable(Color.parseColor(item.color))).into(iv_image)

            if (item.user.total_likes > 0) {
                tv_like.text = item.user.total_likes.toString()
            }else ll_home.visibility = GONE
            tv_title.text = "${item.user.first_name} ${item.user.last_name}"

            iv_image.setOnClickListener {
                imageClick.invoke(item.id)
            }
            iv_more.setOnClickListener {
                val popupMenu = PopupMenu(context, iv_more)

                popupMenu.getMenuInflater().inflate(R.menu.popular_menu, popupMenu.getMenu())
                popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(menuItem: MenuItem): Boolean {
                        var repository = SaveRepository(context)
                        var saveId = SaveId(saveId = item.id, regular = item.urls.regular!!)
                        repository.saveUser(saveId)

                        return true
                    }
                })
                popupMenu.show()
            }
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv_image: ImageView
        val iv_more: ImageView
        val tv_title: TextView
        val tv_like: TextView
        val ll_home: LinearLayout

        init {
            iv_image = view.findViewById(R.id.iv_image)
            iv_more = view.findViewById(R.id.iv_more)
            tv_title = view.findViewById(R.id.tv_title)
            tv_like = view.findViewById(R.id.tv_like)
            ll_home = view.findViewById(R.id.ll_home)
        }
    }
}