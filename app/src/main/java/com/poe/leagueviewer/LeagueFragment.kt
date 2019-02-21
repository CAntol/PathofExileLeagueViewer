package com.poe.leagueviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.poe.leagueviewer.model.LeagueMetaData
import kotlinx.android.synthetic.main.fragment_league.*

class LeagueFragment : Fragment() {

    lateinit var viewModel: LeagueViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(LeagueViewModel::class.java)
        return inflater.inflate(R.layout.fragment_league, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("type")?.let {
            viewModel.getLeagues(it).observe(this, Observer<List<LeagueMetaData>> { data ->
                text_msg.text = data.first().id
            })
        }
    }
}