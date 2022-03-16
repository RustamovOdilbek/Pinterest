package com.example.pinterst.model

import com.example.pinterest.model.home.UserModel

data class ImageModel(
    val urls: UrlModel,
    val id: String,
    val color: String,
    val user: UserModel
    )