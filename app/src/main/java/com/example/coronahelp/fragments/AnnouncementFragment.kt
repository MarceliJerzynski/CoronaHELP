package com.example.coronahelp.fragments

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coronahelp.R
import com.example.coronahelp.rest.RestCaller
import com.example.coronahelp.utils.MapUtils
import com.example.coronahelp.viewModels.AnnouncementViewModel
import com.google.android.gms.maps.model.LatLng
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

        model.announcement.observe(viewLifecycleOwner, Observer {

            it?.let { announcement ->
                title.text = announcement.title
                description.text = announcement.description
                reward.text = announcement.reward.toString()
                val localDateTime: LocalDateTime = LocalDateTime.parse(announcement.time.toString())
                val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                val output: String = formatter.format(localDateTime)
                dateAndTime.text = output
                location.text = MapUtils.getAddress(requireContext(), announcement.location)

                user_name.text = announcement.owner?.name

                if (announcement.owner?.email == RestCaller.email) {
                    deleteTaskButton.visibility = View.VISIBLE
                    takeThisTaskButton.visibility = View.GONE

                    if (announcement.performer != null)
                        endTaskButton.visibility = View.VISIBLE
                    else
                        endTaskButton.visibility = View.GONE
                } else {
                    deleteTaskButton.visibility = View.GONE
                    takeThisTaskButton.visibility = View.VISIBLE
                    endTaskButton.visibility = View.GONE
                }

                if (announcement.performer != null) {
                    takeThisTaskButton.visibility = View.GONE
                    performer_layout.visibility = View.VISIBLE
                    performer.text = announcement.performer!!.name
                } else {
                    performer_layout.visibility = View.GONE
                }

            } ?: run {
                view.findNavController().popBackStack()
            }

        })

        model.success.observe(viewLifecycleOwner, Observer {
            it.let {
                if (it)
                    view.findNavController().popBackStack()
                else {
                    Toast.makeText(requireContext(), RestCaller.lastError, Toast.LENGTH_LONG)
                }
            }
        })

        deleteTaskButton.setOnClickListener {
            model.deleteTask(args.id)
        }

        takeThisTaskButton.setOnClickListener {
            model.takeTask(args.id)
        }

        endTaskButton.setOnClickListener {
            model.confirmAnnouncement(args.id)
        }

    }

}
