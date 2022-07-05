package com.example.benshiaitaskapp.ui.settings

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.benshiaitaskapp.R
import com.example.benshiaitaskapp.data.model.EmailBook
import com.example.benshiaitaskapp.databinding.FragmentPostsDetailBinding
import com.example.benshiaitaskapp.databinding.FragmentSettingsBinding
import com.example.benshiaitaskapp.preference.DataStoreManager
import com.example.benshiaitaskapp.presentation.PostsListViewModel
import com.example.benshiaitaskapp.utils.Constants
import com.example.benshiaitaskapp.utils.Constants.SAVE_EMAIL_KEY
import com.example.benshiaitaskapp.utils.showSnackbar
import com.example.benshiaitaskapp.utils.takeWords
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var dataStoreManager: DataStoreManager
    private val viewModel: PostsListViewModel by activityViewModels()

    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "user"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSettingsBinding.bind(view)
        dataStoreManager = DataStoreManager(requireActivity())
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


        binding.apply {

            button.setOnClickListener {

                if (TextUtils.isEmpty(email.text.toString())) {
                    email.setError(getString(R.string.enter_valid_input))
                    return@setOnClickListener
                }
                else if (!email.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex())) {
                    showSnackbar(emailLayout,  getString(R.string.enter_valid_email))
            } else{
                    val email = email.takeWords()
                    lifecycleScope.launch(Dispatchers.IO) {
                        val emailSaved = EmailBook(email)
                        dataStoreManager.saveEmailtoDataStore(emailSaved)
                    }

                    showSnackbar(emailLayout,  getString(R.string.email_saved))

                }
            }

        }
    }

}