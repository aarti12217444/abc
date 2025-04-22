package com.example.abc

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class TravelActivity : AppCompatActivity() {

    private val travelImages = intArrayOf(
        R.drawable.img_19,
        R.drawable.img_20,
        R.drawable.img_21
    )

    private val imageTitles = arrayOf(
        "Kailash Parvat",
        "Ram Mandir, Ayodhya (UP)",
        "Plain Area of Himachal Pradesh"
    )

    private lateinit var captionTextView: TextView
    private lateinit var flipper: ViewFlipper
    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())
    private val interval: Long = 3000 // 3 seconds

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel)

        flipper = findViewById(R.id.travelFlipper)
        captionTextView = findViewById(R.id.captionTextView)

        for (img in travelImages) {
            val imageView = ImageView(this)
            imageView.setImageResource(img)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            flipper.addView(imageView)
        }

        captionTextView.text = imageTitles[0]

        startAutoFlipping()

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun startAutoFlipping() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                currentIndex = (currentIndex + 1) % travelImages.size
                flipper.showNext()
                captionTextView.text = imageTitles[currentIndex]
                handler.postDelayed(this, interval)
            }
        }, interval)
    }
}
