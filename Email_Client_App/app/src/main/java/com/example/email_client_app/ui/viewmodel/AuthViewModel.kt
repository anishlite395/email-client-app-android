package com.example.email_client_app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.email_client_app.data.dto.RegisterRequest
import com.example.email_client_app.data.local.TokenManager
import com.example.email_client_app.data.remote.ApiService
import com.example.email_client_app.data.remote.RetrofitClient
import com.example.email_client_app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.hilt.navigation.compose.hiltViewModel

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val apiService: ApiService = RetrofitClient.create(tokenManager)
    private val repository = AuthRepository(apiService)


    var uiState by mutableStateOf(RegisterUiState())
        private set

    fun onNameChange(value: String){
        uiState = uiState.copy(name = value)
    }

    fun onEmailChange(value: String){
        uiState = uiState.copy(email = value)
    }

    fun onPasswordChange(value: String){
        uiState = uiState.copy(password = value)
    }

    fun onPConfirmPasswordChange(value: String){
        uiState = uiState.copy(confirmPassword = value)
    }

    fun onLocationChange(value: String){
        uiState = uiState.copy(location = value)
    }

    fun onAgreementChange(value: Boolean){
        uiState = uiState.copy(agreed = value)
    }

    fun Register(){

        if(uiState.password != uiState.confirmPassword){
            uiState = uiState.copy(
                error = "Passwords do not match"
            )
            return
        }

        viewModelScope.launch {
            try{
                uiState = uiState.copy(
                    isloading = true,
                    error = null
                )

                val response = repository.register(

                    RegisterRequest(
                        username = uiState.name,
                        email = uiState.email,
                        password = uiState.password,
                        location = uiState.location
                    )
                )

                if(response.isSuccessful){
                    println("SUCCESS")
                }
                else{
                    uiState = uiState.copy(
                        error = "Registration failed"
                    )
                }
            }catch (e: Exception){
                uiState = uiState.copy(
                    error = e.message
                )

            }finally {
                uiState = uiState.copy(
                    isloading = false
                )
            }
        }
    }




}