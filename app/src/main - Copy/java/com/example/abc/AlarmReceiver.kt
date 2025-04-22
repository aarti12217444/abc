package com.example.abc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Get the current time when the alarm goes off
        val currentTime = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())

        // Show toast with the alarm message and the time
        Toast.makeText(
            context,
            "⏰ Alarm! Time to take a wellness break!\nExercise time: $currentTime\nDo Yoga for 10 minutes!",
            Toast.LENGTH_LONG
        ).show()
    }
}
