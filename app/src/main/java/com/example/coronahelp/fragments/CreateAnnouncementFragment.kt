package com.example.coronahelp.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.icu.text.SimpleDateFormat
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
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList


class CreateAnnouncementFragment : Fragment() {


    private lateinit var viewModel: CreateAnnouncementViewModel

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

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.YEAR)
        val day = calendar.get(Calendar.YEAR)

        //outlinedTextFieldDateTime.setOnClickListener {
        //    val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view,mYear,mMonth,mDay ->
        //outlinedTextFieldDateTime.
        //    })
        //} TODO: DatePickerDialog & TimePickerDialog - nie wiem jak zrobić setText dla textFielda i nigdzie nie mogę znaleźć jak to zrobić
    }
}
