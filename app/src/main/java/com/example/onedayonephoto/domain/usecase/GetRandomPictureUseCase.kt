package com.example.onedayonephoto.domain.usecase

import com.example.onedayonephoto.domain.repository.PictureRepository
import jakarta.inject.Inject

class GetRandomPictureUseCase @Inject constructor(
    private val repository: PictureRepository
) {
    suspend operator fun invoke() = repository.getRandomPicture()
}