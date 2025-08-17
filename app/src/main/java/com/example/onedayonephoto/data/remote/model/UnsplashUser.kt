package com.example.onedayonephoto.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashUser(
    val id: String?,
    val username: String?,
    val name: String?,
    @SerialName("profile_image") val profileImage: UnsplashUserProfileImage?
)