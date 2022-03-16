//package com.example.pinterest.adapter
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.pinterest.R
//
//class FilterAdapter(var context: Context, var items: ArrayList<>):
//    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter_view, parent, false)
//        return FilterViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        var item = items[position]
//        if (holder is FilterViewHolder){
//            var tv_title = holder.tv_title
//
//            tv_title.text = item.tv_title
//
////            tv_title.setOnClickListener {
////                tv_title.setBackgroundResource(R.drawable.border_round_filter_black);
////            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    class FilterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        var tv_title: TextView
//
//        init {
//            tv_title = view.findViewById(R.id.tv_title)
//        }
//    }
//}