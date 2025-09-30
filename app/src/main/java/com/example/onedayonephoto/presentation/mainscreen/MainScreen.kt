package com.example.onedayonephoto.presentation.mainscreen

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.app.WallpaperManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
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
import com.example.onedayonephoto.presentation.notification.LockScreenService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.main_screen) {
    private val viewModel: MainScreenViewModel by viewModels()
    private var binding: MainScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainScreenBinding.bind(view)


        setUpColor()
        setUpListeners()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setUpListeners() {
        binding?.ivPhoto?.setOnLongClickListener {
            viewModel.currentPicture.value?.let { picture ->
                showWallpaperOptionsDialog(picture.pictureUrl)
            }
            true
        }

        binding?.btnNext?.setOnClickListener {
            viewModel.getRandomPicture()
        }

        binding?.btnService?.setOnClickListener {
            viewModel.currentPicture.value?.let { picture ->
                LockScreenService.startService(requireContext(), picture.pictureUrl)
            }
        }

        binding?.btnStopService?.setOnClickListener {
            LockScreenService.stopService(requireContext())
        }
    }

    private fun setUpColor() {

        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.button_color)

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
    }

    private fun showWallpaperOptionsDialog(imageUrl: String) {
        val options = arrayOf("Домашний экран", "Экран блокировки", "Оба экрана")

        AlertDialog.Builder(requireContext())
            .setTitle("Установить обои")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> setWallpaperForHomeScreen(imageUrl)
                    1 -> setWallpaperForLockScreen(imageUrl)
                    2 -> setWallpaperForBoth(imageUrl)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun setWallpaperForHomeScreen(imageUrl: String) {
        setWallpaperWithFlags(imageUrl, WallpaperManager.FLAG_SYSTEM)
    }

    private fun setWallpaperForLockScreen(imageUrl: String) {
        setWallpaperWithFlags(imageUrl, WallpaperManager.FLAG_LOCK)
    }



    private fun setWallpaperForBoth(imageUrl: String) {
        setWallpaperWithFlags(imageUrl, WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
    }

    private fun setWallpaperWithFlags(imageUrl: String, flags: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {

                val bitmap = withContext(Dispatchers.IO) {
                    Glide.with(requireContext())
                        .asBitmap()
                        .load(imageUrl)
                        .submit()
                        .get()
                }

                val wallpaperManager = WallpaperManager.getInstance(requireContext())
                wallpaperManager.setBitmap(bitmap, null, true, flags)

                Toast.makeText(requireContext(), "Обои установлены!", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Log.e("Wallpaper", "Error setting wallpaper", e)
                Toast.makeText(requireContext(), "Ошибка установки обоев", Toast.LENGTH_SHORT).show()
            }
        }
    }

}