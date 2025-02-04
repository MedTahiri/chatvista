package com.mohamed.tahiri.android.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.tahiri.android.model.ApiService
import com.mohamed.tahiri.android.model.Conversation
import com.mohamed.tahiri.android.model.ConversationTitle
import com.mohamed.tahiri.android.model.newConversation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ConversationViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _conversation = mutableStateOf<ApiState<Conversation>>(ApiState.Loading)
    val conversation: State<ApiState<Conversation>> = _conversation

    private val _conversationByUser = mutableStateOf<ApiState<List<ConversationTitle>>>(ApiState.Loading)
    val conversationByUser: State<ApiState<List<ConversationTitle>>> = _conversationByUser

    private val _conversationtitle = mutableStateOf<ApiState<ConversationTitle>>(ApiState.Loading)
    val conversationtitle: State<ApiState<ConversationTitle>> = _conversationtitle


    fun newConversation(conversation: newConversation) {
        viewModelScope.launch {
            try {
                val response = apiService.newConversation(conversation)
                _conversationtitle.value = ApiState.Success(response)
            } catch (e: Exception) {
                _conversationtitle.value = ApiState.Error("Failed to create new conversation : $e")
            }

        }
    }
    /*
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

     */

    fun getConversationByUser(userId: Long) {
        viewModelScope.launch {
            try {
                val response = apiService.getConversationByUser(userId)
                _conversationByUser.value = ApiState.Success(response)
            } catch (e: Exception) {
                _conversationByUser.value = ApiState.Error("Failed to get conversation : $e")
            }

        }
    }

    fun deleteConversation(id: Long) {
        viewModelScope.launch {
            try {
                val response = apiService.deleteConversation(id)
                print(response)
            } catch (e: Exception) {
                print(e)
            }
        }
    }

}