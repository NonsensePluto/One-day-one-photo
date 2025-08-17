package com.example.onedayonephoto.domain.repository

import com.example.onedayonephoto.data.remote.api.UnsplashApiClientImpl
import com.example.onedayonephoto.domain.mapper.UnsplashResponseToDomainPicture
import com.example.onedayonephoto.domain.model.PictureModel
import jakarta.inject.Inject

class PictureRepository @Inject constructor(
    private val unsplashApiClient: UnsplashApiClientImpl,
    private val pictureMapper: UnsplashResponseToDomainPicture
) {
    suspend fun getRandomPicture(): PictureModel
        = pictureMapper(unsplashApiClient.getRandomPicture())
}