package com.example.coronahelp.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coronahelp.R
import com.example.coronahelp.rest.RestCaller
import com.example.coronahelp.utils.MapUtils
import com.example.coronahelp.viewModels.CreateAnnouncementViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.create_announcement_fragment.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CreateAnnouncementFragment : Fragment() {

    val args: CreateAnnouncementFragmentArgs by navArgs()
    val model: CreateAnnouncementViewModel by activityViewModels()

    private fun pickDateTime() {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                TimePickerDialog(
                    requireContext(),
                    TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                        val pickedDateTime = Calendar.getInstance()
                        pickedDateTime.set(year, month, day, hour, minute)
                        editText_date_time.setText(
                            SimpleDateFormat("dd.MM.yyyy HH:mm").format(
                                pickedDateTime.time
                            )
                        )
                    },
                    startHour,
                    startMinute,
                    true
                ).show()
            },
            startYear,
            startMonth,
            startDay
        ).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_announcement_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        editText_location.setText(MapUtils.getAddress(requireContext(), args.location))

        buttonCancelAnnouncement.setOnClickListener {
            view.findNavController().navigate(R.id.action_createAnnouncement2_to_mapsFragment)
        }

        val categories_adapter =
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_menu_popup_item,
                resources.getStringArray(R.array.categories)
            )
        val editTextFilledExposedDropdown: AutoCompleteTextView =
            view.findViewById(R.id.filled_exposed_dropdown)
        editTextFilledExposedDropdown.setAdapter(categories_adapter)


        editText_date_time.setOnClickListener {
            pickDateTime()
        }

        editText_location.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_createAnnouncement2_to_creatingAdMapFragment)
        }

        model.success.observe(viewLifecycleOwner, Observer {

            it.let {
                if (it)
                    view.findNavController()
                        .navigate(R.id.action_createAnnouncement2_to_mapsFragment)
                else {
                    Toast.makeText(
                        activity,
                        RestCaller.lastError,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

        buttonCreateAnnouncement.setOnClickListener {
            val reward = filledTextFieldReward.editText?.text.toString().toDoubleOrNull()
            if (reward === null) {
                Toast.makeText(activity, "Reward have to number", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            model.announcement.title = filledTextFieldTitle.editText?.text.toString()
            model.announcement.description = filledTextFieldDesc.editText?.text.toString()
            model.announcement.reward = reward
            model.announcement.category = outlinedCategoryDropdownMenu.editText?.text.toString()
            model.announcement.location = args.location

            val dateText = filledTextFieldDateTime.editText?.text.toString()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
            model.announcement.time = LocalDateTime.parse(dateText, formatter)

            model.createAnnouncement()
        }
    }
}
