package com.example.pinterest.manager

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.pinterest.model.home.save.SaveId
import com.example.pinterest.newtworking.database.SaveDou

@Database(entities = [SaveId::class], version = 3)
abstract class RoomManager: RoomDatabase() {

    abstract fun saveDau(): SaveDou

    companion object{
        @Volatile
        private var INSTANCE: RoomManager? = null

        fun getDatabase(context: Context): RoomManager{
            synchronized(this){
                var instances = INSTANCE
                if (instances == null){
                    instances = Room.databaseBuilder(
                        context.applicationContext,
                        RoomManager::class.java,
                        "app_db"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()

                    INSTANCE = instances
                }
                return instances
            }
        }
    }
}