package com.example.email_client_app.data.dto

data class RegisterRequest(
 val username: String,
 val email: String,
 val password: String,
 val location: String
)