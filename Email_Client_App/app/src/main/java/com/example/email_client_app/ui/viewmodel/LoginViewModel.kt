package com.example.email_client_app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.email_client_app.data.local.TokenManager
import com.example.email_client_app.data.remote.ApiService
import com.example.email_client_app.data.remote.RetrofitClient
import com.example.email_client_app.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val tokenManager: TokenManager
): ViewModel() {

    private val apiService: ApiService = RetrofitClient.create(tokenManager)
    private val repository = AuthRepository(apiService)



    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(value: String){
        uiState = uiState.copy(email = value)
    }

    fun onPasswordChange(value: String){
        uiState = uiState.copy(password = value)
    }

    fun onAgreedChange(value: Boolean){
        uiState = uiState.copy(agreed = value)
    }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch{
            try{
                uiState = uiState.copy(
                    isLoading = true,
                    error = null
                )

                val response = repository.login(uiState.email,
                                        uiState.password)

                if(response.isSuccessful){
                    val token = response.body()?.token?.toString()
                    if(token != null){
                        tokenManager.saveToken(token)
                    }
                    onSuccess()
                }else{

                    uiState = uiState.copy(
                        error = "Invalid Credentials"
                    )
                }
            }catch (e: Exception){
                uiState = uiState.copy(
                    error = e.message
                )
            } finally {
                uiState = uiState.copy(
                    isLoading = false
                )
            }

        }
    }


}