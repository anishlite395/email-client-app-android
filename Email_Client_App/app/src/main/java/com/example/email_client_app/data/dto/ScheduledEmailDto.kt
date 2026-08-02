package com.example.email_client_app.data.dto

data class ScheduledEmailDto(
    val uid: Long,
    val fromEmail: String,
    val toEmail: String,
    val subject: String,
    val body: String,
    val scheduledAt: String,
    val status: String
)