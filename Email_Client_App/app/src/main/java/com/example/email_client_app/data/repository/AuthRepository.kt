package com.example.email_client_app.data.repository

import com.example.email_client_app.data.dto.DraftsDto
import com.example.email_client_app.data.dto.LoginRequest
import com.example.email_client_app.data.dto.MailRequestDto
import com.example.email_client_app.data.dto.RegisterRequest
import com.example.email_client_app.data.dto.ScheduledEmailDto
import com.example.email_client_app.data.remote.ApiService
import com.example.email_client_app.data.remote.RetrofitClient
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: ApiService) {

    suspend fun register(
        request: RegisterRequest
    ) = api.registerUser(request)

    suspend fun login(
        email: String,
        password: String
    ) = api.loginUser(
        LoginRequest(email,password)
    )

    suspend fun getEmails(
    ) = api.getInbox()

    suspend fun getSentEmails(

    ) = api.getSentEmails()

    suspend fun getDrafts() = api.getDrafts()

    suspend fun saveDraft(
        drafts: DraftsDto
    ) = api.saveDraft(drafts)

    suspend fun deleteDrafts(
        ids: List<Long>
    ) = api.deleteDrafts(ids)

    suspend fun getScheduledEmails(
    ): List<ScheduledEmailDto> = api.getScheduledEmail()







}