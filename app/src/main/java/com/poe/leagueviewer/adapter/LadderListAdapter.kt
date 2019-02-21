package com.poe.leagueviewer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poe.leagueviewer.R
import com.poe.leagueviewer.model.Ladder

class LadderListAdapter : ListAdapter<Ladder, LadderListAdapter.ViewHolder>(LeagueListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_ladder, parent, false))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Ladder) {
            itemView.apply {
                findViewById<TextView>(R.id.txt_rank).text = data.rank?.toString() ?: 0.toString()
                findViewById<TextView>(R.id.txt_char_name).text = data.character?.name
                findViewById<TextView>(R.id.txt_char_class).text = data.character?.poeClass
            }
        }
    }

    private class LeagueListDiffCallback : DiffUtil.ItemCallback<Ladder>() {
        override fun areContentsTheSame(oldItem: Ladder, newItem: Ladder): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Ladder, newItem: Ladder): Boolean {
            return oldItem.rank == newItem.rank
        }
    }
}