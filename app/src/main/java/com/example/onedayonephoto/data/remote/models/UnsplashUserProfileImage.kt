package com.example.onedayonephoto.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class UnsplashUserProfileImage(
    val small: String?,
    val medium: String?,
    val large: String?
)
