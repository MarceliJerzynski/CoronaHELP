package com.example.coronahelp.fragments

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.coronahelp.R
import com.example.coronahelp.viewModels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.login_fragment.registerTextView
import kotlinx.android.synthetic.main.register_fragment.*


class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val model: RegisterViewModel by viewModels()
        model.success.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                view.findNavController().navigate(R.id.action_register_to_login)
            } else {
                val snackBar = Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "Something went wrong, try again with valid data", Snackbar.LENGTH_LONG
                )
                snackBar.show()
            }
        })

        buttonRegister.setOnClickListener {
            val name = editTextRegisterName.text.toString() + " " + editTextRegisterSurname.text.toString()
            val email = editTextRegisterEmail.text.toString()
            val password = editTextRegisterPassword.text.toString()
            val passwordConfirmation = editTextRegisterConfirmPassword.text.toString()

            model.register(name,email,password, passwordConfirmation)
        }

        val ss = SpannableString("Already an user? Sign In!")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                view.findNavController().navigate(R.id.action_register_to_login)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        ss.setSpan(clickableSpan, 17, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val textView = registerTextView
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = Color.TRANSPARENT
    }


}
