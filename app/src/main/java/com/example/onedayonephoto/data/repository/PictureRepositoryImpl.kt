package com.example.onedayonephoto.data.repository

import com.example.onedayonephoto.data.mapper.UnsplashResponseToDomainPicture
import com.example.onedayonephoto.data.remote.api.UnsplashApiClientImpl
import com.example.onedayonephoto.domain.model.PictureModel
import com.example.onedayonephoto.domain.repository.PictureRepository
import jakarta.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val unsplashApiClient: UnsplashApiClientImpl,
    private val pictureMapper: UnsplashResponseToDomainPicture
) : PictureRepository {
    override suspend fun getRandomPicture(): PictureModel
        = pictureMapper(unsplashApiClient.getRandomPicture())
}