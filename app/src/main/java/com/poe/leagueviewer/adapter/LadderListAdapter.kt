package com.poe.leagueviewer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.poe.leagueviewer.R
import com.poe.leagueviewer.model.Ladder

// TODO show loading indicator
class LadderListAdapter(private val onClick: (Ladder) -> Unit) : PagedListAdapter<Ladder, LadderListAdapter.ViewHolder>(LeagueListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        onBindViewHolder(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_ladder, parent, false))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Ladder?) {
            if (data == null) {
                return
            }
            itemView.apply {
                findViewById<TextView>(R.id.txt_rank).text = data.rank?.toString() ?: 0.toString()
                findViewById<TextView>(R.id.txt_char_name).text = data.character?.name
                findViewById<TextView>(R.id.txt_char_class).text = data.character?.poeClass
                findViewById<TextView>(R.id.txt_char_level).text = data.character?.level?.toString()

                val status = findViewById<ImageView>(R.id.img_status)
                if (data.dead == true) {
                    Glide.with(context)
                        .load("http://icons.iconarchive.com/icons/icons8/android/128/Healthcare-Skull-icon.png")
                        .into(status)
                } else {
                    val image = if (data.online == true) R.drawable.ic_online else R.drawable.ic_offline
                    status.setImageResource(image)
                }

                setOnClickListener { onClick(data) }
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