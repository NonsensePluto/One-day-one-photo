package com.example.onedayonephoto.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashLinks(
    val self: String?,
    val html: String?,
    val download: String?,
    @SerialName("download_location") val downloadLocation: String?
)