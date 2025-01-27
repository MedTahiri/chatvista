package com.mohamed.tahiri.android.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.tahiri.android.model.ApiService
import com.mohamed.tahiri.android.model.Message
import com.mohamed.tahiri.android.model.newMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MessageViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _message = mutableStateOf<ApiState<Message>>(ApiState.Loading)
    val message: State<ApiState<Message>> = _message

    private val _messages = mutableStateOf<ApiState<List<Message>>>(ApiState.Loading)
    val messages: State<ApiState<List<Message>>> = _messages


    fun newMessage(message: newMessage) {
        viewModelScope.launch {
            try {
                val response = apiService.newMessage(message)
                _message.value = ApiState.Success(response)
            } catch (e: Exception) {
                _message.value = ApiState.Error("Failed to create new message : $e")
            }

        }
    }

    fun getMessagesByConversation(conversationId : Long){
        viewModelScope.launch {
            try {
                val response = apiService.getMessagesByConversation(conversationId)
                _messages.value = ApiState.Success(response)
            } catch (e: Exception) {
                _messages.value = ApiState.Error("Failed to get messages : $e")
            }

        }
    }


}