package com.mohamed.tahiri.android.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.tahiri.android.model.ApiService
import com.mohamed.tahiri.android.model.Conversation
import com.mohamed.tahiri.android.model.newConversation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ConversationViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _conversation = mutableStateOf<ApiState<Conversation>>(ApiState.Loading)
    val conversation: State<ApiState<Conversation>> = _conversation

    fun newConversation(conversation: newConversation) {
        viewModelScope.launch {
            try {
                val response = apiService.newConversation(conversation)
                _conversation.value = ApiState.Success(response)
            } catch (e: Exception) {
                _conversation.value = ApiState.Error("Failed to create new conversation : $e")
            }

        }
    }

    fun getConversationById(id : Long) {
        viewModelScope.launch {
            try {
                val response = apiService.getConversationById(id)
                _conversation.value = ApiState.Success(response)
            } catch (e: Exception) {
                _conversation.value = ApiState.Error("Failed to get conversation : $e")
            }

        }
    }
}