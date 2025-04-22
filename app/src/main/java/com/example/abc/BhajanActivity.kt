package com.example.abc

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class BhajanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bhajan)

        val radheButton: Button = findViewById(R.id.radheButton)
        val ramButton: Button = findViewById(R.id.ramButton)
        val shivButton: Button = findViewById(R.id.shivButton)
        val hanumanButton: Button = findViewById(R.id.hanumanButton)
        val backButton: Button = findViewById(R.id.backButton)
        val waheguruButton: Button = findViewById(R.id.waheguruButton)
        val allahButton: Button = findViewById(R.id.allahButton)

        radheButton.setOnClickListener {
            openYouTubeLink("https://www.youtube.com/watch?v=Q-0pGqc_IBQ")
        }

        ramButton.setOnClickListener {
            openYouTubeLink("https://www.youtube.com/watch?v=lCzGwuc_VuM")
        }

        shivButton.setOnClickListener {
            openYouTubeLink("https://www.youtube.com/watch?v=S980-z1qx3g")
        }

        hanumanButton.setOnClickListener {
            openYouTubeLink("https://www.youtube.com/watch?v=AETFvQonfV8")
        }

        waheguruButton.setOnClickListener {
            openYouTubeLink("https://www.youtube.com/watch?v=DFVk71WxxBE")
        }
        allahButton.setOnClickListener {
            openYouTubeLink("https://www.youtube.com/watch?v=pAQAp59ZZ50")
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun openYouTubeLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
