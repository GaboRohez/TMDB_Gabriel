package com.gmail.gabow95k.tmdb.service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.gmail.gabow95k.tmdb.ui.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class LocationService : Service() {

    private val TAG = "LocationService"

    private val handler = Handler()
    private lateinit var runnable: Runnable

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        runnable = Runnable {
            saveLocationToFirestore()
            handler.postDelayed(runnable, 10000)
        }
        handler.post(runnable)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Location Updates"
            val descriptionText = "Receive updates on your location"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("location_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notification: Notification = createNotification()
        startForeground(1, notification)
    }

    private fun createNotification(): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, "location_channel")
            .setContentTitle("Location Updates")
            .setContentText("Location updates in progress")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return notificationBuilder.build()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    @SuppressLint("MissingPermission")
    private fun saveLocationToFirestore() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val latitude = location?.latitude
        val longitude = location?.longitude

        val db = FirebaseFirestore.getInstance()
        val coordinates = hashMapOf(
            "latitude" to latitude,
            "longitude" to longitude,
            "date" to SimpleDateFormat("MMMM dd, yyyy hh:mm:ss").format(Date())
        )
        db.collection("coordinates")
            .add(coordinates)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Ubicación guardada con éxito")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error al guardar la ubicación", e)
            }
    }
}