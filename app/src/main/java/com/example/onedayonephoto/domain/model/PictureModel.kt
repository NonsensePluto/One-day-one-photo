package com.example.onedayonephoto.domain.model

data class PictureModel (
    val pictureId: String,
    val pictureUrl: String,
    val pictureDate: String,
    val pictureDescription: String,
    val photographer: String
)