package com.example.onedayonephoto.domain.repository

import com.example.onedayonephoto.domain.model.PictureModel

interface PictureRepository {
    suspend fun getRandomPicture(): PictureModel
}