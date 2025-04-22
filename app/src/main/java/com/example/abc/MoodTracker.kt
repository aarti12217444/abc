package com.example.abc

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.Timestamp
import java.util.*

class MoodTrackerActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var moodRadioGroup: RadioGroup
    private lateinit var submitButton: Button
    private lateinit var moodListView: ListView
    private lateinit var moodAdapter: ArrayAdapter<String>
    private val moodList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_tracker)

        db = FirebaseFirestore.getInstance()
        moodRadioGroup = findViewById(R.id.moodRadioGroup)
        submitButton = findViewById(R.id.submitMoodButton)
        moodListView = findViewById(R.id.moodListView)

        moodAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, moodList)
        moodListView.adapter = moodAdapter

        submitButton.setOnClickListener {
            saveMood()
        }

        loadMoodHistory()
    }

    private fun saveMood() {
        val selectedId = moodRadioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(this, "Please select a mood!", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedMood = findViewById<RadioButton>(selectedId).text.toString()

        // Get the current time as a Firestore timestamp
        val timestamp = Timestamp(Date())

        val moodEntry = hashMapOf(
            "mood" to selectedMood,
            "timestamp" to timestamp
        )

        db.collection("moods").add(moodEntry)
            .addOnSuccessListener {
                Toast.makeText(this, "Mood saved!", Toast.LENGTH_SHORT).show()
                loadMoodHistory()  // Refresh the list
                moodRadioGroup.clearCheck()  // Clear the selection after submission
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save mood", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadMoodHistory() {
        // Order by timestamp in descending order to show the most recent mood first
        db.collection("moods")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                moodList.clear()  // Clear existing list to refresh the display
                for (doc in documents) {
                    val mood = doc.getString("mood") ?: "Unknown"
                    val timestamp = doc.getTimestamp("timestamp") ?: Timestamp.now()
                    val time = timestamp.toDate().toString()  // Convert timestamp to readable string
                    moodList.add("$time - $mood")
                }
                moodAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load mood history", Toast.LENGTH_SHORT).show()
            }
    }
}
