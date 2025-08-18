package com.example.onedayonephoto.presentation.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.onedayonephoto.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LockScreenService: Service() {
    private lateinit var notificationManager: NotificationManager
    private var pictureUrl: String? = null
    private val glide by lazy { Glide.with(this) }
    private val serviceScope = CoroutineScope(Dispatchers.IO)


    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getStringExtra("picture_url")?.let {
            pictureUrl = it
            loadAndShowImage(it)
        }
        return START_NOT_STICKY
    }


    override fun onBind(p0: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    private fun createNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Photo",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Displays photos on lock screen"
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                setShowBadge(false)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("RemoteViewLayout")
    private fun showLockScreenNotification(bitmap: Bitmap) {
        val notificationView = RemoteViews(packageName, R.layout.lock_screen_notification)
        notificationView.setImageViewBitmap(R.id.photo_view, bitmap)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setCustomContentView(notificationView)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setOngoing(true)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun loadAndShowImage(url: String) {
        serviceScope.launch {
            try {
                val futureTarget = glide.asBitmap()
                    .load(url)
                    .submit(NOTIFICATION_WIDTH, NOTIFICATION_HEIGHT)

                val bitmap = futureTarget.get()
                showLockScreenNotification(bitmap)
                glide.clear(futureTarget)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val CHANNEL_ID = "lock_screen_service"
        const val NOTIFICATION_WIDTH = 600
        const val NOTIFICATION_HEIGHT = 400
        const val NOTIFICATION_ID = 1

        fun startService(context: Context, photoUrl: String) {
            val intent = Intent(context, LockScreenService::class.java).apply {
                putExtra("picture_url", photoUrl)
            }
            ContextCompat.startForegroundService(context, intent)
        }

        fun stopService(context: Context) {
            context.stopService(Intent(context, LockScreenService::class.java))
        }
    }

}