package com.example.onedayonephoto.data.remote.api

import com.example.onedayonephoto.data.remote.models.UnsplashPhotoResponse

interface UnsplashApiClient {
    suspend fun getRandomPhoto(): UnsplashPhotoResponse?
}
