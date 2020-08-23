package com.example.coronahelp.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coronahelp.adapters.AnnouncementRecyclerAdapter
import com.example.coronahelp.DataSource
import com.example.coronahelp.R
import kotlinx.android.synthetic.main.announcement_list_fragment.*


class AnnouncementListFragment : Fragment() {

    private lateinit var viewModel: AnnouncementListViewModel
    private lateinit var announcementAdapter: AnnouncementRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initRecyclerView()
        addDataSet()

        return inflater.inflate(R.layout.announcement_list_fragment, container, false)
    }

    private fun addDataSet(){
        val data = DataSource.createDataSet()
        announcementAdapter.submitList(data)
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            announcementAdapter =
                AnnouncementRecyclerAdapter()
            adapter = announcementAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AnnouncementListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
