package com.example.email_client_app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.email_client_app.data.dto.DraftsDto
import com.example.email_client_app.data.dto.EmailDto
import com.example.email_client_app.data.dto.MailRequestDto
import com.example.email_client_app.data.repository.AuthRepository
import com.example.email_client_app.data.repository.DraftsRepository
import com.example.email_client_app.data.repository.SendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val repository: SendRepository,
    private val draftRepository: DraftsRepository
): ViewModel()
{
    fun sendEmail(
        to: String,
        subject: String,
        body: String,
        scheduledAt: String?,
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
                        scheduledAt = scheduledAt,
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

    fun saveDraft(
        to: String,
        subject: String,
        body: String,
        onSuccess: () -> Unit
    ){

        viewModelScope.launch {
            try{
                draftRepository.saveDraft(
                    DraftsDto(
                        uid = 0,
                        from = "",
                        to = to,
                        subject = subject,
                        sentDate = null,
                        read = false,
                        body = body
                    )
                )

                onSuccess()

                Log.d("DRAFT","Drafts Saved")
            }catch (e: Exception){
                Log.e(
                    "DRAFT",
                    "Draft save failed",
                    e
                )
            }
        }
    }


}