package com.example.pinterest.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest.R
import com.example.pinterest.model.home.save.SaveId

class SaveAdapter(val context: Context, val items: ArrayList<SaveId>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var imageClick: ((String) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_save_view, parent, false)
        return SaveViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = items[position]
        if (holder is SaveViewHolder){
            val iv_image = holder.iv_image

            Glide.with(iv_image.context).load(item.regular).placeholder(ColorDrawable(Color.GRAY)).into(iv_image)

            iv_image.setOnClickListener {
                imageClick.invoke(item.saveId)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SaveViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iv_image: ImageView
        init {
            iv_image = view.findViewById(R.id.iv_image)
        }
    }
}