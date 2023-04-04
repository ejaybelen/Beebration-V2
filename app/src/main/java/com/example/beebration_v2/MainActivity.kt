package com.example.beebration_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var textViewVoltage: TextView
    private lateinit var textViewBatteryPercentage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        // Get a reference to the Firebase Realtime Database
        database = FirebaseDatabase.getInstance("https://beebration-v2-52386-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        // Initialize UI elements
        textViewVoltage = findViewById(R.id.textView_voltage)
        textViewBatteryPercentage = findViewById(R.id.textView_batteryPercentage)

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
                    // Handle the Voltage data fetched from the Realtime Database
                    val voltageValue = dataSnapshot.child("Voltage").getValue()
                    Log.d(TAG, "Voltage value is: $voltageValue")
                    // Update the TextView with the fetched Voltage data
                    when (voltageValue) {
                        is Long -> textViewVoltage.text = "Voltage: ${voltageValue}"
                        is Double -> textViewVoltage.text = "Voltage: ${voltageValue}"
                        else -> textViewVoltage.text = "Voltage: No data available"
                    }

                    // Handle the Battery Percentage data fetched from the Realtime Database
                    val batteryPercentageValue = dataSnapshot.child("Battery Percentage").getValue()
                    Log.d(TAG, "Battery Percentage value is: $batteryPercentageValue")

                    // Update the TextView with the fetched Battery Percentage data
                    when (batteryPercentageValue) {
                        is Long -> textViewBatteryPercentage.text = "Battery Percentage: ${batteryPercentageValue}"
                        is Double -> textViewBatteryPercentage.text = "Battery Percentage: ${batteryPercentageValue}"
                        else -> textViewBatteryPercentage.text = "Battery Percentage: No data available"
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

