package com.example.pinterest.holder

import android.content.Context
import androidx.core.content.edit

class SharePref(context: Context) {
    private val pref = context.getSharedPreferences("Loading", Context.MODE_PRIVATE)

    var isSaved: Boolean
    get() = pref.getBoolean("isSaved", false)
        set(value) = pref.edit { this.putBoolean("isSaved", value) }
}
