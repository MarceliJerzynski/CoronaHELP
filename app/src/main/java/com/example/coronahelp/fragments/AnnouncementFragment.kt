package com.example.coronahelp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.coronahelp.R
import kotlinx.android.synthetic.main.announcement_fragment.*


class AnnouncementFragment : Fragment() {

    //private lateinit var viewModel: AnnouncementListViewModel

    val args: AnnouncementFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.announcement_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title.text = args.announcement.title
    }
}
