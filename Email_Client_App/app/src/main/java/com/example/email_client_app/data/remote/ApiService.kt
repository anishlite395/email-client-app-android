package com.example.email_client_app.data.remote

import com.example.email_client_app.data.dto.DraftsDto
import com.example.email_client_app.data.dto.EmailDto
import com.example.email_client_app.data.dto.LoginRequest
import com.example.email_client_app.data.dto.LoginResponse
import com.example.email_client_app.data.dto.MailRequestDto
import com.example.email_client_app.data.dto.RegisterRequest
import com.example.email_client_app.data.dto.RegisterResponse
import com.example.email_client_app.data.dto.ScheduledEmailDto
import com.example.email_client_app.data.dto.SendApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{

    @POST("/auth/register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST(value = "/auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("/inbox")
    suspend fun getInbox(): List<EmailDto>

    @GET("/sent")
    suspend fun getSentEmails(): List<EmailDto>

    @GET("/drafts")
    suspend fun getDrafts(): List<DraftsDto>

    @POST("/drafts")
    suspend fun saveDraft(
        @Body draft: DraftsDto
    )

    @HTTP(
        method = "DELETE",
        path = "/drafts/delete",
        hasBody = true
    )
    suspend fun deleteDrafts(
        @Body uids: List<Long>
    )


    @GET("inbox/{uid}")
    suspend fun getEmail(
        @Path("uid") uid: Long
    ): EmailDto

    @PUT("inbox/{uid}/read")
    suspend fun markAsRead(
        @Path("uid") uid: Long,
        @Query("read") read: Boolean
    )

    @HTTP(method = "DELETE", path = "inbox/delete", hasBody = true)
    suspend fun deleteEmails(
        @Body uids: List<Long>
    )

    // =========================
    // EMAIL SERVICE (NEW BACKEND)
    // =========================

    @POST("/email/send")
    suspend fun sendEmail(
        @Body request: MailRequestDto
    ): Response<SendApiResponse>

    @POST("/email/reply")
    suspend fun replyEmail(
        @Body request: MailRequestDto
    )

    @GET("/email/scheduled")
    suspend fun getScheduledEmail(): List<ScheduledEmailDto>
}