package Model

import androidx.annotation.DrawableRes

data class Rider(
    val nama: String,
    val deskripsi: String,
    @DrawableRes val imagesRes: Int
)
