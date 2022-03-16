package com.example.pinterest.newtworking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    var BASE_URL = "https://api.unsplash.com/"
    var API_KEY = "q4wZ0LROjdlnrDp_KpgoJ-8J-JvauiKLY5LVI-BBrKo"

    var retrofit: Retrofit? = null

    fun getApiInterface(): ApiService {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(ApiService::class.java)
    }
}