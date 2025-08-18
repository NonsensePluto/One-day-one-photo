package com.example.onedayonephoto.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashUser (
    @SerialName("id")
    val id: String?,
    @SerialName("username")
    val username: String?,
)