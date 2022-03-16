package com.example.pinterest.newtworking.database

import android.content.Context
import com.example.pinterest.manager.RoomManager
import com.example.pinterest.model.home.save.SaveId

class SaveRepository {
    var saveDou: SaveDou

    constructor(context: Context){
        val db = RoomManager.getDatabase(context)
        saveDou = db.saveDau()
    }
    fun getUsers(): List<SaveId>{
        return saveDou.getImage()
    }

    fun saveUser(saveId: SaveId){
        saveDou.saveImage(saveId)
    }
}