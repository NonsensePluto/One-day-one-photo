package com.example.onedayonephoto.presentation.mainscreen

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.onedayonephoto.R
import com.example.onedayonephoto.databinding.MainScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import androidx.core.graphics.toColorInt
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.main_screen) {
    private val viewModel: MainScreenViewModel by viewModels()
    private var binding: MainScreenBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainScreenBinding.bind(view)

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.button_color)


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            var lastColorInt = "#fae7b5".toColorInt()
            viewModel.currentPicture.collectLatest { picture ->
                val url = picture?.pictureUrl
                if (!url.isNullOrEmpty()) {
                    Glide.with(requireContext())
                        .load(url)
                        .transition(DrawableTransitionOptions.withCrossFade(500)) // плавное появление фото
                        .into(binding!!.ivPhoto)
                }
                picture?.color?.let { colorString ->
                    val newColorInt = colorString.toColorInt()
                    // Плавная анимация цвета фона
                    val animator = ValueAnimator.ofArgb(lastColorInt, newColorInt)
                    animator.duration = 700
                    animator.interpolator = AccelerateDecelerateInterpolator()
                    animator.addUpdateListener {
                        binding?.root?.setBackgroundColor(it.animatedValue as Int)
                    }
                    animator.start()
                    lastColorInt = newColorInt
                }
            }
        }

        binding?.btnNext?.setOnClickListener {
            viewModel.getRandomPicture()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}