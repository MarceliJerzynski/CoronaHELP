package com.example.coronahelp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coronahelp.adapters.AnnouncementRecyclerAdapter
import com.example.coronahelp.DataSource
import com.example.coronahelp.R


class AnnouncementFragment : Fragment() {

    //private lateinit var viewModel: AnnouncementListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.announcement_fragment, container, false)
    }

}
