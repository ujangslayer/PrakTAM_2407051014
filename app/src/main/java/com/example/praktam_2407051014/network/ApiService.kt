package com.example.praktam_2407051014.network

import com.example.praktam_2407051014.Model.Rider
import retrofit2.http.GET

interface ApiService {
    @GET("list_rider.json")
    suspend fun getRider(): List<Rider>

}
