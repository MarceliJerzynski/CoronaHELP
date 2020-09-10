package com.example.coronahelp.fragments

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.coronahelp.R
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.announcement_fragment.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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
        description.text = args.announcement.description
        reward.text = args.announcement.reward.toString()
        val localDateTime: LocalDateTime = LocalDateTime.parse(args.announcement.time.toString())
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        val output: String = formatter.format(localDateTime)
        dateAndTime.text = output
        val address = getAddress(args.announcement.location)
        location.text = address
        //location.text = args.announcement.location.toString()
        //TODO uncomment if owner done
//        user_name.text = args.announcement.owner;

    }

    private fun getAddress(lat: LatLng): String? {
        val geocoder = Geocoder(requireContext())
        val list = geocoder.getFromLocation(lat.latitude, lat.longitude,1)
        return list[0].getAddressLine(0)
    }
}
