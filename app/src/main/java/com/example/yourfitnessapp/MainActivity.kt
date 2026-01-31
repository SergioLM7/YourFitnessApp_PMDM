package com.example.yourfitnessapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourfitnessapp.model.ActivitySession
import java.text.SimpleDateFormat

/**
 * Clase para la actividad principal
 */
class MainActivity : AppCompatActivity() {

    /**
     * Adapter para el RecyclerView
     */
    private lateinit var adapter : ActivitySessionAdapter

    /**
     * Método que se ejecuta al crear la actividad
     * @param savedInstanceState Bundle con el estado de la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSaveActivity = findViewById<Button>(R.id.btnSaveActivity)
        val btnRegisterActivity = findViewById<Button>(R.id.btnRegisterActivity)
        val rv = findViewById<RecyclerView>(R.id.rvActivities)

        adapter = ActivitySessionAdapter()
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        btnSaveActivity.setOnClickListener {
            saveActivity()
        }

        btnRegisterActivity.setOnClickListener {
            val intent = Intent(this, AccelerometerActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Método que se ejecuta al pulsar el botón "Save Activity"
     * Guarda la actividad en el adapter
     */
    private fun saveActivity() {
        val etActivity = findViewById<EditText>(R.id.etActivity)
        val etDuration = findViewById<EditText>(R.id.etDuration)
        val etType = findViewById<EditText>(R.id.etType)

        val activityName = etActivity.text.toString().trim();
        val duration = etDuration.text.toString().trim();
        val type = etType.text.toString().trim();

        if(activityName.isEmpty() || activityName.length < 3 || activityName.length > 20) {
            etActivity.error = "Activity name is required (min: 3 characters - max: 20)"
        } else if (duration.isEmpty() || duration.toInt() <= 0) {
            etDuration.error = "Duration is required and must be a positive number"
        } else if (type.isEmpty() || type.length < 3 || type.length > 20) {
            etType.error = "Type is required (min: 3 characters - max: 20)"
        } else {
            val date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(System.currentTimeMillis())

            val activityToAdd = ActivitySession(
                activityName,
                "Duration: $duration min.",
                "Type: $type",
                "Date & Time: $date"
            )

            adapter.addItem(activityToAdd)

            etActivity.text.clear()
            etDuration.text.clear()
            etType.text.clear()
        }
    }

}