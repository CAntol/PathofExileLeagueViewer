package com.poe.leagueviewer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poe.leagueviewer.R
import com.poe.leagueviewer.model.LeagueMetaData

class LeagueListAdapter(val onClick: (String) -> Unit) : ListAdapter<LeagueMetaData, LeagueListAdapter.ViewHolder>(LeagueListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_league, parent, false))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: LeagueMetaData, onClick: (String) -> Unit) {
            itemView.apply {
                findViewById<TextView>(R.id.txt_league_id).text = data.id
                findViewById<TextView>(R.id.txt_date_start).text = data.startAt // TODO format
                findViewById<TextView>(R.id.txt_date_end).text = data.endAt // TODO format
                findViewById<TextView>(R.id.txt_forum_link).text = data.url // TODO format
                findViewById<CheckBox>(R.id.checkbox_delve).isChecked = data.delveEvent ?: false
                setOnClickListener {
                    data.id?.let { onClick(it) }
                }
            }
        }
    }

    private class LeagueListDiffCallback : DiffUtil.ItemCallback<LeagueMetaData>() {
        override fun areItemsTheSame(oldItem: LeagueMetaData, newItem: LeagueMetaData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LeagueMetaData, newItem: LeagueMetaData): Boolean {
            return oldItem == newItem
        }
    }
}