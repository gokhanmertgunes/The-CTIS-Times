package com.thectistimes.web.api

import com.thectistimes.web.model.New
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    //7FDI
    @GET("12VP")
    fun getNews(): Call<List<New>>
}
