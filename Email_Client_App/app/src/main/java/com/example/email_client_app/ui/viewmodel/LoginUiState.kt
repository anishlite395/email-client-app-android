package com.example.email_client_app.ui.viewmodel

data class LoginUiState(
    val email: String = "",

    val password: String = "",

    val agreed: Boolean = false,

    val isLoading: Boolean = false,

    val error: String? = null
)
