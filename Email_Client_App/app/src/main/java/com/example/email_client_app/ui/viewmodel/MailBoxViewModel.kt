package com.example.email_client_app.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.email_client_app.data.dto.DraftsDto
import com.example.email_client_app.data.dto.EmailDto
import com.example.email_client_app.data.dto.MailBoxType
import com.example.email_client_app.data.local.TokenManager
import com.example.email_client_app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MailboxViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _emails = mutableStateListOf<EmailDto>()
    private val _drafts = mutableStateListOf<DraftsDto>()

    val emails: List<EmailDto> = _emails

    fun loadMailbox(type: MailBoxType) {

        viewModelScope.launch {

            try {

                when (type) {

                    MailBoxType.INBOX -> {
                        _emails.clear()
                        _emails.addAll(repository.getEmails())
                    }


                    MailBoxType.SENT -> {
                        _emails.clear()
                        _emails.addAll(repository.getSentEmails())
                    }


                    MailBoxType.DRAFTS -> {
                        val drafts = repository.getDrafts()
                        Log.d("DRAFTS","Loaded ${drafts.size} drafts")
                        _emails.clear()
                        _emails.addAll(
                            drafts.map {
                                d -> EmailDto(
                                    uid = d.uid,
                                    from = d.from,
                                    to = d.to,
                                    subject = d.subject,
                                    sentDate = d.sentDate ?:"",
                                    body = d.body,
                                    read = d.read
                                )
                            }
                        )
                    }


                }


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun logout(onDone: () -> Unit) {

        viewModelScope.launch {

            tokenManager.clearToken()

            _emails.clear()

            onDone()
        }
    }
}