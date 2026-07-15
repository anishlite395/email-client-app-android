package com.example.email_client_app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.email_client_app.data.dto.EmailDto
import com.example.email_client_app.data.dto.MailRequestDto
import com.example.email_client_app.data.repository.AuthRepository
import com.example.email_client_app.data.repository.SendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val repository: SendRepository
): ViewModel()
{
    fun sendEmail(
        to: String,
        subject: String,
        body: String,
        onSuccess: () -> Unit
    ){

        viewModelScope.launch {
            try{
                repository.sendEmail(
                    MailRequestDto(
                        to = to,
                        subject = subject,
                        body = body,
                        html = false,
                        scheduledAt = null,
                        inReplyToMessageId = null,
                        references = null
                    )
                )
                onSuccess()
            }catch (e: Exception){
                Log.e("COMPOSE","Send Failed",e)
            }
        }
    }


}