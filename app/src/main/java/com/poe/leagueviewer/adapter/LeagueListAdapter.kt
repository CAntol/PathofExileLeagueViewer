package com.poe.leagueviewer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poe.leagueviewer.R
import com.poe.leagueviewer.model.LeagueMetaData
import java.text.SimpleDateFormat
import java.util.*

class LeagueListAdapter(private val onClick: (String) -> Unit, private val loadUrl: (String) -> Unit)
    : ListAdapter<LeagueMetaData, LeagueListAdapter.ViewHolder>(LeagueListDiffCallback()) {

    private val formatIn = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    private val formatOut = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_league, parent, false))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: LeagueMetaData, onClick: (String) -> Unit) {
            itemView.apply {
                findViewById<TextView>(R.id.txt_league_id).text = data.id
                data.startAt?.let {
                    findViewById<TextView>(R.id.txt_date_start).text = getDate(it)
                }
                val endDate = data.endAt
                if (endDate == null) {
                    findViewById<TextView>(R.id.label_date_end).visibility = View.GONE
                    findViewById<TextView>(R.id.txt_date_end).visibility = View.GONE
                } else {
                    findViewById<TextView>(R.id.label_date_end).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.txt_date_end).apply {
                        text = getDate(endDate)
                        visibility = View.VISIBLE
                    }
                }
                findViewById<TextView>(R.id.txt_forum_link).apply {
                    val url = data.url
                    if (url == null) {
                        visibility = View.GONE
                    } else {
                        text = context.getString(R.string.txt_view_on_poe)
                        setOnClickListener {
                            loadUrl(url)
                        }
                        visibility = View.VISIBLE
                    }
                }
                if (data.delveEvent == true) {
                    findViewById<TextView>(R.id.txt_delve).visibility = View.VISIBLE
                }
                setOnClickListener {
                    data.id?.let { onClick(it) }
                }
            }
        }

        private fun getDate(rawDate: String): String {
            val dateTime = formatIn.parse(rawDate)
            return formatOut.format(dateTime)
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