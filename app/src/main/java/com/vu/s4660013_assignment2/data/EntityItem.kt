package com.vu.s4660013_assignment2.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EntityItem(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val rating: Float = 0f,
    val imageUrl: String = ""
) : Parcelable