package com.example.onedayonephoto.presentation.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.onedayonephoto.domain.model.PictureModel
import com.example.onedayonephoto.domain.usecase.GetRandomPictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getRandomPictureUseCase: GetRandomPictureUseCase
) : ViewModel() {

    private val _currentPicture = MutableStateFlow<PictureModel?>(null)
    val currentPicture: StateFlow<PictureModel?> = _currentPicture

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        getRandomPicture()
    }

    fun getRandomPicture() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _currentPicture.value = getRandomPictureUseCase()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}