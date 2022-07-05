package com.example.benshiaitaskapp.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.benshiaitaskapp.data.model.EmailBook
import com.example.benshiaitaskapp.utils.Constants.DATASTORENAME
import com.example.benshiaitaskapp.utils.Constants.SAVE_EMAIL_KEY
import kotlinx.coroutines.flow.map

class DataStoreManager(val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORENAME)



suspend fun saveEmailtoDataStore(phonebook: EmailBook) {
    context.dataStore.edit {

        it[SAVE_EMAIL_KEY] = phonebook.email
    }
}

suspend fun getFromDataStore() = context.dataStore.data.map { email ->

    EmailBook(
        email = email[SAVE_EMAIL_KEY]?:""
    )
}


}