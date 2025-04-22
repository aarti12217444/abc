package com.example.abc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔹 Add Data
        findViewById<Button>(R.id.btnAddData).setOnClickListener {
            startActivity(Intent(this, AddDataActivity::class.java))
        }

        // 🔹 Fetch Data
        findViewById<Button>(R.id.btnFetchData).setOnClickListener {
            startActivity(Intent(this, FetchDataActivity::class.java))
        }

        // 🔹 Update Data
        findViewById<Button>(R.id.btnUpdateData).setOnClickListener {
            startActivity(Intent(this, UpdateDataActivity::class.java))
        }

        // 🔹 Delete Data
        findViewById<Button>(R.id.btnDeleteData).setOnClickListener {
            startActivity(Intent(this, DeleteDataActivity::class.java))
        }

        // 🔹 Mood Tracker
        findViewById<Button>(R.id.btnMoodTracker).setOnClickListener {
            startActivity(Intent(this, MoodTrackerActivity::class.java))
        }

        // 🔹 Self-Assessment Quiz
        findViewById<Button>(R.id.btnSelfAssessment).setOnClickListener {
            startActivity(Intent(this, SelfAssessmentActivity::class.java))
        }

        // 🔹 Fitness Resources
        findViewById<Button>(R.id.btnFitnessResources).setOnClickListener {
            startActivity(Intent(this, FitnessActivity::class.java))
        }

        // 🔹 Take Quiz
        findViewById<Button>(R.id.btnQuiz).setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }

        // 🔹 Toggle Language (can be improved further with real switch)
        findViewById<Button>(R.id.btnToggleLanguage).setOnClickListener {
            Toast.makeText(this, "Language switching feature coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
}
