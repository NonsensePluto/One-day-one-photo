package com.example.onedayonephoto.di

import com.example.onedayonephoto.data.repository.PictureRepositoryImpl
import com.example.onedayonephoto.domain.repository.PictureRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPictureRepository(
        pictureRepositoryImpl: PictureRepositoryImpl
    ): PictureRepository

}