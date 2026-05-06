package com.example.praktam_2407051014.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "https://gist.githubusercontent.com/ujangslayer/d745ec77695046e5419af185b1efb803/raw/5f6a94b89ee4d747dcfd63081019d82bf475255e/"



    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
