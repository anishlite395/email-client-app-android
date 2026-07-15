package com.example.email_client_app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.email_client_app.data.dto.EmailDto
import com.example.email_client_app.data.repository.AuthRepository
import com.example.email_client_app.data.repository.InboxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailDetailViewModel @Inject constructor(
    private val repository: InboxRepository
) : ViewModel() {

    var email by mutableStateOf<EmailDto?>(null)
        private set

    var loading by mutableStateOf(false)
        private set

    fun loadEmail(uid: Long) {

        viewModelScope.launch {
            loading = true

            try{

                val result = repository.getEmail(uid)

                email = result

                repository.markRead(uid,true)
            } catch (e: Exception){
                e.printStackTrace()
            }

            loading = false
        }
    }
}