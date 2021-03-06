package com.example.coronahelp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.coronahelp.R
import com.example.coronahelp.fragments.MapsFragmentDirections
import com.example.coronahelp.model.Announcement
import kotlinx.android.synthetic.main.announcement_fragment.view.*
import kotlinx.android.synthetic.main.layout_announcement_list_item.view.*
import kotlinx.android.synthetic.main.layout_announcement_list_item.view.reward
import kotlinx.android.synthetic.main.announcement_fragment.view.description as description1
import kotlinx.android.synthetic.main.announcement_fragment.view.title as title1
import kotlinx.android.synthetic.main.announcement_fragment.view.user_name as user_name1

class AnnouncementRecyclerAdapter(private var items:List<Announcement>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AnnouncementViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_announcement_list_item, parent, false)
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
        notifyDataSetChanged()
    }

    class AnnouncementViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){

        lateinit var announcement: Announcement

        init{
            itemView.setOnClickListener {
                val action = MapsFragmentDirections.actionMapsFragmentToAnnouncementFragment(announcement.id)
                itemView.findNavController().navigate(action)
            }
        }

        fun bind(announcement: Announcement){
            this.announcement = announcement
            with(itemView)
            {
                title.text = announcement.title
                description.text = announcement.description
                reward.text = announcement.reward.toString()
                user_name.text = announcement.owner?.name
            }

        }
    }
}