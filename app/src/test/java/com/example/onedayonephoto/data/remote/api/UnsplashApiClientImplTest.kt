package com.example.onedayonephoto.data.remote.api

import org.junit.Assert.assertNotNull
import org.junit.Test
import kotlinx.coroutines.runBlocking

class UnsplashApiClientImplTest {

    private val client = UnsplashApiClientImpl()

    @Test
    fun testGetRandomPhotoReturnsValidResponse() = runBlocking {
        val result = client.getRandomPhoto()
        assertNotNull("Returned photo should not be null", result)
        println("Received photo: $result")
        assertNotNull(result?.id)
        assertNotNull(result?.urls?.regular)
    }
}
