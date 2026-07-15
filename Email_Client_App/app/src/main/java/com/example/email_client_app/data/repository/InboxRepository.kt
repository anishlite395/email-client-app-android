package com.example.email_client_app.data.repository

import com.example.email_client_app.data.dto.EmailDto
import com.example.email_client_app.data.remote.ApiService
import javax.inject.Inject

class InboxRepository @Inject constructor(private val api: ApiService) {

    suspend fun getEmails(
    ) = api.getInbox()

    suspend fun getEmail(uid: Long) =
        api.getEmail(uid)

    suspend fun markRead(
        uid: Long,
        read: Boolean
    ) = api.markAsRead(uid, read)

    suspend fun deleteEmails(
        uids: List<Long>
    ) = api.deleteEmails(uids)

}