package com.example.onedayonephoto.data.remote.api

import com.example.onedayonephoto.data.remote.model.UnsplashPhotoResponse

interface UnsplashApiClient {
    suspend fun getRandomPicture(): UnsplashPhotoResponse?
}
