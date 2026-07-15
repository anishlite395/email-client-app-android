package com.example.email_client_app.data.repository

import com.example.email_client_app.data.dto.MailRequestDto
import com.example.email_client_app.data.remote.ApiService
import javax.inject.Inject

class SendRepository @Inject constructor(private val api: ApiService) {


    suspend fun sendEmail(
        request: MailRequestDto
    ) = api.sendEmail(request)
}