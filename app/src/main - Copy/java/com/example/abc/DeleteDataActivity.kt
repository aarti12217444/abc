package com.example.abc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class DeleteDataActivity : AppCompatActivity() {

    private lateinit var edtDeleteEmail: EditText
    private lateinit var btnDelete: Button

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_data)

        edtDeleteEmail = findViewById(R.id.edtDeleteEmail)
        btnDelete = findViewById(R.id.btnDelete)

        btnDelete.setOnClickListener {
            val email = edtDeleteEmail.text.toString().trim()

            if (email.isNotEmpty()) {
                db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            for (doc in documents) {
                                db.collection("users").document(doc.id)
                                    .delete()
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Deleted successfully ✅", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Delete failed ❌", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Toast.makeText(this, "User not found ⚠️", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Enter Email to delete", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
