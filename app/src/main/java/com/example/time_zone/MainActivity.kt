package com.example.time_zone
import android.content.pm.ActivityInfo
import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import android.view.WindowManager



class MainActivity : Activity() {
    private lateinit var textViewTime: TextView
    private val executor = Executors.newSingleThreadScheduledExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewTime = findViewById(R.id.time)
        executor.scheduleAtFixedRate(updateTimeRunnable, 0, 1, TimeUnit.SECONDS)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)




    }

    private val updateTimeRunnable = Runnable {
        val currentTime = System.currentTimeMillis()
        val is24HourFormat = android.text.format.DateFormat.is24HourFormat(this)
        val timeFormatPattern = if (is24HourFormat) "HH:mm:ss " else "hh:mm:ss "
        val dateFormat = SimpleDateFormat(timeFormatPattern, java.util.Locale.getDefault())
        val formattedTime = dateFormat.format(currentTime)

        runOnUiThread {
            textViewTime.text = formattedTime
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdown()

    }
}
