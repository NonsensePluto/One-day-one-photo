package com.example.onedayonephoto.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashPhotoResponse(
    val id: String,
    val slug: String,
    @SerialName("alternative_slugs") val alternativeSlugs: Map<String, String>,
    @SerialName("created_at") val createdAt: String?,
    @SerialName("updated_at") val updatedAt: String?,
    @SerialName("promoted_at") val promotedAt: String?,
    val width: Int?,
    val height: Int?,
    val color: String?,
    @SerialName("blur_hash") val blurHash: String?,
    val description: String?,
    @SerialName("alt_description") val altDescription: String?,
    val urls: UnsplashPhotoUrls,
    val links: UnsplashLinks,
    val likes: Int?,
    @SerialName("liked_by_user") val likedByUser: Boolean?,
    val user: UnsplashUser?
)