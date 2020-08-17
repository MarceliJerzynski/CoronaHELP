package com.example.coronahelp.viewModels

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.coronahelp.R

class CreateAnnouncement : Fragment() {

    companion object {
        fun newInstance() = CreateAnnouncement()
    }

    private lateinit var viewModel: CreateAnnouncementViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_announcement_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateAnnouncementViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
