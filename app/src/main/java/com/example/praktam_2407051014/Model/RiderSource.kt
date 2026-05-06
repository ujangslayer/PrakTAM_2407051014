package com.example.praktam_2407051014.Model

import android.content.Context
object RiderSource {
    fun getResourceId(context: Context, imageName: String): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }
}