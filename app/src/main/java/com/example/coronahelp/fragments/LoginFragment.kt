package com.example.coronahelp.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

        val ss = SpannableString("Don't have an account? Sign Up")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                view.findNavController().navigate(R.id.action_login_to_register)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        ss.setSpan(clickableSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val textView = registerTextView
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = Color.TRANSPARENT

    }
}
