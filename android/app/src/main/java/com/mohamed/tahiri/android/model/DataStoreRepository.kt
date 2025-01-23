package com.mohamed.tahiri.android.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store_preferences")

@Singleton

class DataStoreRepository @Inject constructor(
    private val context: Context
) {
    private companion object {
        val USER_ID = longPreferencesKey("user_id")
        val USER_FULL_NAME = stringPreferencesKey("user_full_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_PASSWORD = stringPreferencesKey("user_password")
        val USER_IMAGE = stringPreferencesKey("user_image")
        val USER_CONVERSATIONS_ID = stringPreferencesKey("user_conversations_id")
    }

    val currentUserId: Flow<Long> = context.dataStore.data.map { preferences ->
        preferences[USER_ID] ?: -1
    }
    val userFullName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_FULL_NAME] ?: ""
    }
    val userEmail: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_EMAIL] ?: ""
    }
    val userPassword: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_PASSWORD] ?: ""
    }
    val userImage: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_IMAGE] ?: ""
    }
    val userConversationsId: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_CONVERSATIONS_ID] ?: ""
    }

    suspend fun saveUserId(userId: Long) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    suspend fun saveUserFullName(fullName: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_FULL_NAME] = fullName
        }
    }

    suspend fun saveUserEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL] = email
        }
    }

    suspend fun saveUserPassword(password: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_PASSWORD] = password
        }
    }

    suspend fun saveUserImage(imageUrl: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_IMAGE] = imageUrl
        }
    }

    suspend fun saveUserConversationsId(conversationsId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_CONVERSATIONS_ID] = conversationsId
        }
    }

    suspend fun clearUserPreferences() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}
