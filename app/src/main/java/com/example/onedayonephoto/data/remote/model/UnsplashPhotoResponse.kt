package com.example.onedayonephoto.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashPhotoResponse(
    @SerialName("id")
    val id: String,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("color")
    val color: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("urls")
    val urls: UnsplashPhotoUrls,
    @SerialName("links")
    val links: UnsplashLinks,
    @SerialName("likes")
    val likes: Int?,
    @SerialName("user")
    val user: UnsplashUser?
)