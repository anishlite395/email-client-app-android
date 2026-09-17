package com.example.email_client_app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.email_client_app.data.dto.EmailDto
import com.example.email_client_app.data.dto.MailRequestDto
import com.example.email_client_app.data.repository.InboxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReplyEmailViewModel @Inject constructor(
    private val repository: InboxRepository
) : ViewModel() {

    var email by mutableStateOf<EmailDto?>(null)
        private set

    var replyBody by mutableStateOf("")
        private set

    var loading by mutableStateOf(false)
        private set

    var sending by mutableStateOf(false)
        private set

    var sent by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun loadEmail(uid: Long) {

        viewModelScope.launch {

            loading = true
            error = null

            try {

                email = repository.getEmail(uid)

            } catch (e: Exception) {

                error =
                    e.message ?: "Failed to load email"

            } finally {

                loading = false
            }
        }
    }

    fun updateReplyBody(body: String) {
        replyBody = body
    }

    fun sendReply() {

        val originalEmail = email
            ?: return

        if (replyBody.isBlank()) {
            return
        }

        viewModelScope.launch {

            sending = true
            error = null

            try {

                val subject =
                    if (
                        originalEmail.subject.startsWith(
                            "Re:",
                            ignoreCase = true
                        )
                    ) {
                        originalEmail.subject
                    } else {
                        "Re: ${originalEmail.subject}"
                    }

                /*
                 * Build the References chain.
                 */
                val references = buildList {

                    addAll(
                        originalEmail.references
                    )

                    originalEmail.messageId?.let {

                        if (!contains(it)) {
                            add(it)
                        }
                    }
                }

                val request =
                    MailRequestDto(

                        to = originalEmail.from,

                        subject = subject,

                        body = replyBody,

                        html = false,

                        scheduledAt = null,

                        inReplyToMessageId =
                            originalEmail.messageId,

                        references =
                            references.toTypedArray()
                    )

                repository.replyEmail(request)

                sent = true

            } catch (e: Exception) {

                error =
                    e.message
                        ?: "Failed to send reply"

            } finally {

                sending = false
            }
        }
    }

}