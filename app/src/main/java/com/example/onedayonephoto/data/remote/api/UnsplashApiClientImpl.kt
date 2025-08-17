package com.example.onedayonephoto.data.remote.api

import android.util.Log
import com.example.onedayonephoto.BuildConfig
import com.example.onedayonephoto.data.remote.model.UnsplashPhotoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import jakarta.inject.Inject
import kotlinx.serialization.json.Json

class UnsplashApiClientImpl @Inject constructor() : UnsplashApiClient{
    private val BASE_URL = "https://api.unsplash.com/photos/random"

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun getRandomPicture(): UnsplashPhotoResponse {
        try {
            val response: UnsplashPhotoResponse = client.get(BASE_URL) {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "client_id=${BuildConfig.API_TOKEN}"
                    )
                }
            }.body()
            return response
        } catch (e: Exception) {
            Log.e("UnsplashApiClient", "Exception: ", e)
            throw e
        }
    }
}