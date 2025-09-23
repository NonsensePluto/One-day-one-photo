package com.example.onedayonephoto.data.mapper

import com.example.onedayonephoto.data.remote.model.UnsplashPhotoResponse
import com.example.onedayonephoto.domain.model.PictureModel
import jakarta.inject.Inject

class UnsplashResponseToDomainPicture @Inject constructor() {
    operator fun invoke(randomPicture: UnsplashPhotoResponse): PictureModel {
        return PictureModel(
            pictureId = randomPicture.id,
            pictureUrl = randomPicture.urls.regular,
            pictureDate = randomPicture.createdAt?: "No date available",
            pictureDescription = randomPicture.description ?: "No description available",
            photographer = randomPicture.user?.username ?: "Unknown",
            color = randomPicture.color
        )
    }
}