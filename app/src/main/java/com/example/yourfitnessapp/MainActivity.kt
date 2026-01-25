package com.example.yourfitnessapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSaveActivity = findViewById<Button>(R.id.btnSaveActivity)
        val btnRegisterActivity = findViewById<Button>(R.id.btnRegisterActivity)
        val rv = findViewById<RecyclerView>(R.id.rvActivities)

        val adapter = ActivitySessionAdapter()
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        btnSaveActivity.setOnClickListener {
            saveActivity()
        }

        btnRegisterActivity.setOnClickListener {
            registerActivity()
        }

    }

    private fun saveActivity() {
        //TODO
    }

    private fun registerActivity() {
        //TODO
    }
}