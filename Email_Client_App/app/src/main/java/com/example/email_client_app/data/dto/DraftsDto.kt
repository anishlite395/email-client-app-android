package com.example.email_client_app.data.dto

data class DraftsDto(
    val uid: Long,
    val from: String,
    val to: String,
    val subject: String,
    val sentDate: String?,
    val read: Boolean,
    val body: String
)