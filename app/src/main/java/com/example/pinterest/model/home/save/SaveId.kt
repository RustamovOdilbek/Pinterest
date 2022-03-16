package com.example.pinterest.model.home.save

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pinterst.model.UrlModel

@Entity(tableName = "save_table")
class SaveId (
    @PrimaryKey(autoGenerate = true)
    var count: Int? = null,

    @ColumnInfo(name = "time")
    val saveId: String,

    @ColumnInfo(name = "image")
    val regular: String
    )