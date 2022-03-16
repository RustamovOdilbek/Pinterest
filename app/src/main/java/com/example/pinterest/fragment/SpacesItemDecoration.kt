package com.example.pinterest.fragment

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(private val i: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = i
        outRect.bottom = i
        outRect.right = i

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = i
        }
    }
}