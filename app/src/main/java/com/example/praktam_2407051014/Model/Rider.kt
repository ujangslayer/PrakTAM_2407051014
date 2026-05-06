package com.example.praktam_2407051014.Model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Url

data class Rider(
    @SerializedName("nama")
    val nama: String,

    @SerializedName("deskripsi")
    val deskripsi: String,

    @SerializedName("image_url")
 val imageUrl: String
)
