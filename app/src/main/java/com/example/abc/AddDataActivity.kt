package com.example.abc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddDataActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var btnSave: Button

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        edtName = findViewById(R.id.edtName)
        edtEmail = findViewById(R.id.edtEmail)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            val name = edtName.text.toString().trim()
            val email = edtEmail.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                val userMap = hashMapOf(
                    "name" to name,
                    "email" to email
                )

                db.collection("users")
                    .add(userMap)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data Added ✅", Toast.LENGTH_SHORT).show()
                        edtName.text.clear()
                        edtEmail.text.clear()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to Add ❌", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
