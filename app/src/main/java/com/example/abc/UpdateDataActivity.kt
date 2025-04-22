package com.example.abc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class UpdateDataActivity : AppCompatActivity() {

    private lateinit var edtOldEmail: EditText
    private lateinit var edtNewName: EditText
    private lateinit var btnUpdate: Button

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)

        edtOldEmail = findViewById(R.id.edtOldEmail)
        edtNewName = findViewById(R.id.edtNewName)
        btnUpdate = findViewById(R.id.btnUpdate)

        btnUpdate.setOnClickListener {
            val email = edtOldEmail.text.toString().trim()
            val newName = edtNewName.text.toString().trim()

            if (email.isNotEmpty() && newName.isNotEmpty()) {
                db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            for (doc in documents) {
                                db.collection("users").document(doc.id)
                                    .update("name", newName)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Name updated ✅", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Update failed ❌", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Toast.makeText(this, "User not found ⚠️", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
