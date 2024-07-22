package com.thectistimes.web.api

import com.thectistimes.web.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: Retrofit? = null


    fun getClient(): Retrofit {
        if (retrofit == null) {
            val client = OkHttpClient.Builder()
                .hostnameVerifier { _, _ -> true }
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit as Retrofit
    }
}