package com.example.email_client_app.data.dto

data class EmailDto(
    val uid: Long,

    val from: String,

    val to: String,

    val subject: String,

    val body: String,

    val sentDate: String,

    val read: Boolean
)