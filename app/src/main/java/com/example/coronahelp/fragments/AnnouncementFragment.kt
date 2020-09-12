package com.example.coronahelp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.coronahelp.R
import com.example.coronahelp.viewModels.AnnouncementViewModel
import kotlinx.android.synthetic.main.announcement_fragment.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class AnnouncementFragment : Fragment() {

    private lateinit var viewModel: AnnouncementViewModel

    val args: AnnouncementFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.announcement_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val model: AnnouncementViewModel by viewModels()
        model.getAnnouncement(args.id)

        model.announcement.observe( viewLifecycleOwner, Observer { announcement ->
            title.text = announcement.title
            description.text = announcement.description
            reward.text = announcement.reward.toString()
            val localDateTime: LocalDateTime = LocalDateTime.parse(announcement.time.toString())
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
            val output: String = formatter.format(localDateTime)
            dateAndTime.text = output
            location.text = announcement.location.toString()
            //TODO uncomment if owner done
//        user_name.text = args.announcement.owner;
        })

    }
}
