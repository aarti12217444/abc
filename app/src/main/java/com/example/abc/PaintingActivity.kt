package com.example.abc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity

class PaintingActivity : AppCompatActivity() {

    private val images = intArrayOf(
        R.drawable.img_18,
        R.drawable.img_22,
        R.drawable.img_23
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_painting)

        val flipper: ViewFlipper = findViewById(R.id.paintingFlipper)
        for (img in images) {
            val imageView = ImageView(this)
            imageView.setImageResource(img)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            flipper.addView(imageView)
        }

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish() // goes back to previous activity (FitnessActivity)
        }
    }
}
