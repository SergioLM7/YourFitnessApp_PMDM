package com.example.yourfitnessapp.model

import kotlin.time.Duration

data class ActivitySession (
    val activityName: String,
    val duration: String,
    val type: String,
    val date: String
)