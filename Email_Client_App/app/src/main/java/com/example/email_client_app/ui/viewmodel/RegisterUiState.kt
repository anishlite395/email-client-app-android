package com.example.email_client_app.ui.viewmodel

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val location: String = "",
    val agreed: Boolean = false,

    val isloading: Boolean = false,

    val error: String? = null,

    val successMessage: String? = null
)