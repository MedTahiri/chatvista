package com.mohamed.tahiri.android.viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.tahiri.android.model.ApiService
import com.mohamed.tahiri.android.model.User
import com.mohamed.tahiri.android.model.newUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _users = mutableStateOf<ApiState<List<User>>>(ApiState.Loading)
    val users: State<ApiState<List<User>>> = _users

    private val _user = mutableStateOf<ApiState<User>>(ApiState.Loading)
    val user: State<ApiState<User>> = _user

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = apiService.getUsers()
                _users.value = ApiState.Success(response)
            } catch (e: Exception) {
                _users.value = ApiState.Error("Failed to fetch data : $e")
            }
        }
    }

    fun createUser(user: newUser) {
        viewModelScope.launch {
            try {
                val response = apiService.newUser(user)
                _user.value = ApiState.Success(response)
            } catch (e: Exception) {
                _user.value = ApiState.Error("Failed to create new user : $e")
            }

        }
    }

    fun getUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getUser(email, password)
                _user.value = ApiState.Success(response)
            } catch (e: Exception) {
                _user.value = ApiState.Error("Faild to acces to your user : $e")
            }
        }
    }
}

sealed class ApiState<out T> {
    object Loading : ApiState<Nothing>()
    data class Success<out T>(val data: T) : ApiState<T>()
    data class Error(val message: String) : ApiState<Nothing>()
}