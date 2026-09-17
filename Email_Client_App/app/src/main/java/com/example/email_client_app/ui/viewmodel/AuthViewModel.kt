//package com.example.email_client_app.ui.viewmodel
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.email_client_app.data.dto.RegisterRequest
//import com.example.email_client_app.data.local.TokenManager
//import com.example.email_client_app.data.remote.ApiService
//import com.example.email_client_app.data.remote.RetrofitClient
//import com.example.email_client_app.data.repository.AuthRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//import androidx.hilt.navigation.compose.hiltViewModel
//
//@HiltViewModel
//class AuthViewModel @Inject constructor(
//    private val tokenManager: TokenManager
//) : ViewModel() {
//
//    private val apiService: ApiService = RetrofitClient.create(tokenManager)
//    private val repository = AuthRepository(apiService)
//
//
//    var uiState by mutableStateOf(RegisterUiState())
//        private set
//
//    fun onNameChange(value: String){
//        uiState = uiState.copy(name = value)
//    }
//
//    fun onEmailChange(value: String){
//        uiState = uiState.copy(email = value)
//    }
//
//    fun onPasswordChange(value: String){
//        uiState = uiState.copy(password = value)
//    }
//
//    fun onPConfirmPasswordChange(value: String){
//        uiState = uiState.copy(confirmPassword = value)
//    }
//
//    fun onLocationChange(value: String){
//        uiState = uiState.copy(location = value)
//    }
//
//    fun onAgreementChange(value: Boolean){
//        uiState = uiState.copy(agreed = value)
//    }
//
//    fun Register(){
//
//        if(uiState.password != uiState.confirmPassword){
//            uiState = uiState.copy(
//                error = "Passwords do not match"
//            )
//            return
//        }
//
//        viewModelScope.launch {
//            try{
//                uiState = uiState.copy(
//                    isloading = true,
//                    error = null
//                )
//
//                val response = repository.register(
//
//                    RegisterRequest(
//                        username = uiState.name,
//                        email = uiState.email,
//                        password = uiState.password,
//                        location = uiState.location
//                    )
//                )
//
//                if(response.isSuccessful){
//                    println("SUCCESS")
//                }
//                else{
//                    uiState = uiState.copy(
//                        error = "Registration failed"
//                    )
//                }
//            }catch (e: Exception){
//                uiState = uiState.copy(
//                    error = e.message
//                )
//
//            }finally {
//                uiState = uiState.copy(
//                    isloading = false
//                )
//            }
//        }
//    }
//
//
//
//
//}

package com.example.email_client_app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.email_client_app.data.dto.RegisterRequest
import com.example.email_client_app.data.local.TokenManager
import com.example.email_client_app.data.remote.ApiService
import com.example.email_client_app.data.remote.RetrofitClient
import com.example.email_client_app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val apiService: ApiService =
        RetrofitClient.create(tokenManager)

    private val repository =
        AuthRepository(apiService)

    // IMPORTANT:
    // Compose must observe this state
    var uiState by mutableStateOf(RegisterUiState())
        private set


    // ----------------------------------
    // Name
    // ----------------------------------

    fun onNameChange(value: String) {

        uiState = uiState.copy(
            name = value,
            error = null
        )
    }


    // ----------------------------------
    // Email
    // ----------------------------------

    fun onEmailChange(value: String) {

        uiState = uiState.copy(
            email = value,
            error = null
        )
    }


    // ----------------------------------
    // Password
    // ----------------------------------

    fun onPasswordChange(value: String) {

        uiState = uiState.copy(
            password = value,
            error = null
        )
    }


    // ----------------------------------
    // Confirm Password
    // ----------------------------------

    fun onPConfirmPasswordChange(value: String) {

        uiState = uiState.copy(
            confirmPassword = value,
            error = null
        )
    }


    // ----------------------------------
    // Location
    // ----------------------------------

    fun onLocationChange(value: String) {

        uiState = uiState.copy(
            location = value,
            error = null
        )
    }


    // ----------------------------------
    // Terms & Privacy
    // ----------------------------------

    fun onAgreementChange(value: Boolean) {

        uiState = uiState.copy(
            agreed = value,
            error = null
        )
    }


    // ----------------------------------
    // Register
    // ----------------------------------

    fun Register() {

        // -------------------------
        // Validation
        // -------------------------

        if (uiState.name.isBlank()) {

            uiState = uiState.copy(
                error = "Please enter your full name",
                successMessage = null
            )

            return
        }

        if (uiState.email.isBlank()) {

            uiState = uiState.copy(
                error = "Please enter your email address",
                successMessage = null
            )

            return
        }

        if (uiState.password.isBlank()) {

            uiState = uiState.copy(
                error = "Please enter a password",
                successMessage = null
            )

            return
        }

        if (uiState.password != uiState.confirmPassword) {

            uiState = uiState.copy(
                error = "Passwords do not match",
                successMessage = null
            )

            return
        }

        if (!uiState.agreed) {

            uiState = uiState.copy(
                error = "Please agree to the Terms & Privacy Policy",
                successMessage = null
            )

            return
        }


        // -------------------------
        // Registration API call
        // -------------------------

        viewModelScope.launch {

            try {

                uiState = uiState.copy(
                    isloading = true,
                    error = null,
                    successMessage = null
                )

                val response = repository.register(

                    RegisterRequest(
                        username = uiState.name,
                        email = uiState.email,
                        password = uiState.password,
                        location = uiState.location
                    )
                )


                if (response.isSuccessful) {

                    uiState = uiState.copy(
                        isloading = false,
                        error = null,
                        successMessage = "Registration successful!"
                    )

                } else {

                    uiState = uiState.copy(
                        isloading = false,
                        error = "Registration failed",
                        successMessage = null
                    )
                }

            } catch (e: Exception) {

                uiState = uiState.copy(
                    isloading = false,
                    error = e.message
                        ?: "Something went wrong",
                    successMessage = null
                )
            }
        }
    }
}