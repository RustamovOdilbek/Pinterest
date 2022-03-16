package com.example.pinterest.newtworking

import com.example.pinterst.model.ImageModel
import com.example.pinterst.model.SearchModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: Client-ID q4wZ0LROjdlnrDp_KpgoJ-8J-JvauiKLY5LVI-BBrKo")
    @GET("photos")
    fun getImages(@Query("page") page: Int,
                  @Query("per_page")perPage: Int = 10):
            Call<List<ImageModel>>

    @Headers("Authorization: Client-ID q4wZ0LROjdlnrDp_KpgoJ-8J-JvauiKLY5LVI-BBrKo")
    @GET("photos/{id}")
    fun getImage(@Path("id") id: String): Call<ImageModel>

    @Headers("Authorization: Client-ID q4wZ0LROjdlnrDp_KpgoJ-8J-JvauiKLY5LVI-BBrKo")
    @GET("search/photos")
    fun searchImages(@Query("query") query: String,
                     @Query("per_page")perPage: Int):
            Call<SearchModel>
}