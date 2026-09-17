package com.example.email_client_app.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.email_client_app.data.dto.DraftsDto
import com.example.email_client_app.data.repository.DraftsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DraftsViewModel @Inject constructor(
    private val repository: DraftsRepository
): ViewModel()  {

    var drafts by  mutableStateOf<List<DraftsDto>>(emptyList())
        private set

    init {
        loadDrafts()
    }

    fun loadDrafts(){

        viewModelScope.launch {
            drafts = repository.getDrafts()
        }
    }




}