package com.example.abc

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class FetchDataActivity : AppCompatActivity() {

    private lateinit var txtResult: TextView
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_data)

        txtResult = findViewById(R.id.txtResult)

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val data = StringBuilder()
                for (document in result) {
                    val name = document.getString("name")
                    val email = document.getString("email")
                    data.append("🧍 Name: $name\n📧 Email: $email\n\n")
                }
                txtResult.text = if (data.isNotEmpty()) data.toString() else "No data found."
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error fetching data 😞", Toast.LENGTH_SHORT).show()
            }
    }
}
