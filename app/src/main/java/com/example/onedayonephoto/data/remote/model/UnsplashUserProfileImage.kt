package com.example.onedayonephoto.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class UnsplashUserProfileImage(
    val small: String?,
    val medium: String?,
    val large: String?
)
