package com.mohamed.tahiri.android.model

import com.mohamed.tahiri.android.R

class ImageMapper {

    private val images = mapOf(
        "0" to R.drawable.a,
        "1" to R.drawable.b,
        "2" to R.drawable.c,
        "3" to R.drawable.d,
        "4" to R.drawable.e,
        "5" to R.drawable.f,
        "6" to R.drawable.g,
        "7" to R.drawable.h,
        "8" to R.drawable.i,
        "9" to R.drawable.j
    )


    public fun getImage(key: String): Int? {
        return images[key]
    }

}

