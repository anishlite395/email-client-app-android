package com.example.email_client_app.data.repository

import com.example.email_client_app.data.dto.EmailDto
import com.example.email_client_app.data.remote.ApiService
import javax.inject.Inject

class SentRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getSentEmail(uid: Long): EmailDto {
        return apiService.getSentEmail(uid)
    }

    suspend fun markAsRead(uid: Long) {
        apiService.markSentAsRead(uid)
    }
}