package com.example.beebration_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var textViewData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        // Get a reference to the Firebase Realtime Database
        database = FirebaseDatabase.getInstance("https://beebration-v2-52386-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        // Initialize UI elements
        textViewData = findViewById(R.id.textView_data)

        // Fetch data and listen for changes
        fetchData()
    }

    private fun fetchData() {
        // Get a reference to the "data" child node in the Realtime Database
        val dataRef = database.child("data")

        // Attach a ValueEventListener to the "data" child node to listen for changes
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    // Handle the data fetched from the Realtime Database
                    val value = dataSnapshot.child("Voltage").getValue(Long::class.java)
                    Log.d(TAG, "Value is: $value")
                    // Update the TextView with the fetched data
                    if (value != null) {
                        textViewData.text = "Voltage: $value"
                    } else {
                        textViewData.text = "No data available"
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to read value.", e)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that occur while fetching the data
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}