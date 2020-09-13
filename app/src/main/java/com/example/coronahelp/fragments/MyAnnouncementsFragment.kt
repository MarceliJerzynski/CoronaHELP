package com.example.coronahelp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.coronahelp.R
import com.example.coronahelp.adapters.MyAnnouncementRecyclerAdapter
import com.example.coronahelp.rest.RestCaller
import com.example.coronahelp.viewModels.MapsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_my_announcements.*

class MyAnnouncementsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_announcements, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(activity)
        recycler_view_my_tasks.layoutManager = layoutManager
        recycler_view_my_tasks.adapter = MyAnnouncementRecyclerAdapter(mutableListOf())

        val model: MapsFragmentViewModel by viewModels()
        model.refreshAnnouncements()
        model.announcements.observe(viewLifecycleOwner, Observer {
            (recycler_view_my_tasks.adapter as MyAnnouncementRecyclerAdapter).submitList(it.filter { announcement ->
                announcement.owner?.email == RestCaller.email || announcement.performer?.email == RestCaller.email
            })
        })
    }
}
