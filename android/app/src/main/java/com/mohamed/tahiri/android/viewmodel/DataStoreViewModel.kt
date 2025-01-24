package com.mohamed.tahiri.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.tahiri.android.model.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DataStoreViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) : ViewModel() {
    val userId: Flow<Long> = dataStoreRepository.currentUserId
//    val userFullName: Flow<String> = dataStoreRepository.userFullName
//    val userEmail: Flow<String> = dataStoreRepository.userEmail
//    val userPassword: Flow<String> = dataStoreRepository.userPassword
//    val userImage: Flow<String> = dataStoreRepository.userImage
//    val userConversationsId: Flow<String> = dataStoreRepository.userConversationsId

    fun saveUserId(userId: Long) {
        viewModelScope.launch {
            dataStoreRepository.saveUserId(userId)
        }
    }

//    fun saveUserFullName(fullName: String) {
//        viewModelScope.launch {
//            dataStoreRepository.saveUserFullName(fullName)
//        }
//    }
//
//    fun saveUserEmail(email: String) {
//        viewModelScope.launch {
//            dataStoreRepository.saveUserEmail(email)
//        }
//    }
//
//    fun saveUserPassword(password: String) {
//        viewModelScope.launch {
//            dataStoreRepository.saveUserPassword(password)
//        }
//    }
//
//    fun saveUserImage(imageUrl: String) {
//        viewModelScope.launch {
//            dataStoreRepository.saveUserImage(imageUrl)
//        }
//    }
//
//    fun saveUserConversationsId(conversationsId: String) {
//        viewModelScope.launch {
//            dataStoreRepository.saveUserConversationsId(conversationsId)
//        }
//    }

    fun cleardataStoreRepository() {
        viewModelScope.launch {
            dataStoreRepository.clearUserPreferences()
        }
    }
}