package com.example.email_client_app.data.repository

import com.example.email_client_app.data.dto.DraftsDto
import com.example.email_client_app.data.remote.ApiService
import javax.inject.Inject

class DraftsRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getDrafts() = api.getDrafts()

    suspend fun saveDraft(
        drafts: DraftsDto
    ) = api.saveDraft(drafts)

    suspend fun getDraft(
        uid: Long
    ) = api.getDraft(uid)



}