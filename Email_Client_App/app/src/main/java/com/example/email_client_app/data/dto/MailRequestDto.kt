package com.example.email_client_app.data.dto

data class MailRequestDto(
    val to: String,
    val subject: String,
    val body: String,
    val html: Boolean,
    val scheduledAt: String?,
    val inReplyToMessageId: String?,
    val references: Array<String>?
)
