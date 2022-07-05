package com.example.benshiaitaskapp.utils

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    const val POST_URL = "posts"
    const val AUTHOR_URL = "users"
    const val DATASTORENAME = "users_data"
    val SAVE_EMAIL_KEY = stringPreferencesKey("save_email")

}