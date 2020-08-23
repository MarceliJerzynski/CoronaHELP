package com.example.coronahelp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.coronahelp.R
import com.example.coronahelp.model.Announcement
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.layout_announcement_list_item.view.*

class AnnouncementRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var items: List<Announcement> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AnnouncementViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_announcement_list_item,
                parent,
                false
            )
        )
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is AnnouncementViewHolder ->{
                holder.bind(items.get(position))
            }
        }
    }

    fun submitList(announcementList: List<Announcement>){
        items = announcementList
    }

    class AnnouncementViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val announcementTitle = itemView.announcement_title
        val announcementDesc = itemView.announcement_desc
        val announcementLocalization = itemView.announcement_localization
        val announcementTime = itemView.announcement_time

        init{
            itemView.setOnClickListener {
                itemView.findNavController().navigate(R.id.action_mapsFragment_to_announcementList)
            }
        }

        fun bind(announcement: Announcement){
            announcementTitle.setText(announcement.title)
            announcementDesc.setText(announcement.description)
            announcementLocalization.text = announcement.location.toString()
            announcementTime.setText(announcement.time.toString())
        }
    }



}