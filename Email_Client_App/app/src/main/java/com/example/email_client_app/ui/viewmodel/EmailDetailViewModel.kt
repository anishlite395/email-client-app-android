package com.example.email_client_app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.email_client_app.data.dto.EmailDto
import com.example.email_client_app.data.dto.MailBoxType
import com.example.email_client_app.data.repository.AuthRepository
import com.example.email_client_app.data.repository.InboxRepository
import com.example.email_client_app.data.repository.SentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailDetailViewModel @Inject constructor(
    private val repository: InboxRepository,
    private val sentRepository: SentRepository
) : ViewModel() {

    var email by mutableStateOf<EmailDto?>(null)
        private set

    var loading by mutableStateOf(false)
        private set

    fun loadEmail(uid: Long,
                  mailboxType: MailBoxType
    ) {

        viewModelScope.launch {
            loading = true

            try{

                when(mailboxType){
                    MailBoxType.INBOX -> {
                        val result = repository.getEmail(uid)

                        email = result

                        repository.markRead(uid,true)

                    }

                    MailBoxType.SENT -> {

                        val result =
                            sentRepository.getSentEmail(uid)

                        email = result

                        sentRepository.markAsRead(uid)
                    }

                    else -> {

                    }
                }

                            } catch (e: Exception){
                e.printStackTrace()
            }

            loading = false
        }
    }
}