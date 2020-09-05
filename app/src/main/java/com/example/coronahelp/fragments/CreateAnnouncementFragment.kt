package com.example.coronahelp.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.coronahelp.R
import com.example.coronahelp.model.Category
import com.example.coronahelp.viewModels.CreateAnnouncementViewModel
import kotlinx.android.synthetic.main.create_announcement_fragment.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CreateAnnouncementFragment : Fragment() {


    private lateinit var viewModel: CreateAnnouncementViewModel

    private fun pickDateTime() {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
            TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                editText_date_time.setText(SimpleDateFormat("dd.MM.yyyy HH:mm").format(pickedDateTime.time))
            }, startHour, startMinute, true).show()
        }, startYear, startMonth, startDay).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.create_announcement_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonCancelAnnouncement.setOnClickListener {
            view.findNavController().navigate(R.id.action_createAnnouncement2_to_mapsFragment)
        }

        val categories: MutableList<Category> = ArrayList()
        for (category in Category.values()) {
            categories.add(category)
        }
        val categories_adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_popup_item, categories)
        val editTextFilledExposedDropdown: AutoCompleteTextView = view.findViewById(R.id.filled_exposed_dropdown)
        editTextFilledExposedDropdown.setAdapter(categories_adapter)


        editText_date_time.setOnClickListener {
            pickDateTime()
        }
    }
}
