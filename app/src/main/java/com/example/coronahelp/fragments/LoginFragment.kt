package com.example.coronahelp.fragments

import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import com.example.coronahelp.R
import com.example.coronahelp.model.LoginParams
import com.example.coronahelp.rest.RestCaller
import com.example.coronahelp.viewModels.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        buttonLogin.setOnClickListener {
            val email = editTextLogin.text.toString()
            val password = editTextPassword.text.toString()
            val loginParams = LoginParams(email, password, Build.MODEL)

            GlobalScope.launch {
                val response = RestCaller.postLogin(loginParams)

                if (response)
                    view.findNavController().navigate(R.id.action_login_to_mapsFragment)
                else {
                    // TODO print error for user
                }
            }

        }
    }
}
