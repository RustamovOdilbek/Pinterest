package com.example.pinterest.newtworking.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pinterest.model.home.save.SaveId

@Dao
interface SaveDou {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveImage(saveId: SaveId)

    @Query("SELECT * FROM save_table")
    fun getImage(): List<SaveId>
}