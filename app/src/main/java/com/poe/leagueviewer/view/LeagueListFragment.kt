package com.poe.leagueviewer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poe.leagueviewer.viewmodels.LeagueViewModel
import com.poe.leagueviewer.R
import com.poe.leagueviewer.adapter.LeagueListAdapter
import com.poe.leagueviewer.data.LeagueRepository
import com.poe.leagueviewer.model.LeagueMetaData
import com.poe.leagueviewer.utils.KEY_LEAGUE_ID
import com.poe.leagueviewer.utils.KEY_TYPE
import com.poe.leagueviewer.viewmodels.LeagueViewModelFactory
import java.lang.Exception

class LeagueListFragment : Fragment() {

    lateinit var viewModel: LeagueViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // TODO - DI
        val factory = LeagueViewModelFactory(LeagueRepository.getInstance())
        viewModel = activity?.run {
            ViewModelProviders.of(this, factory).get(LeagueViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        return inflater.inflate(R.layout.fragment_league_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = LeagueListAdapter { id -> showLadder(id) }
        view.findViewById<RecyclerView>(R.id.list_leagues).apply {
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation))
        }
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: LeagueListAdapter) {
        arguments?.getString(KEY_TYPE)?.let {
            viewModel.getLeagues(it).observe(this, Observer<List<LeagueMetaData>> { data ->
                adapter.submitList(data)
            })
        }
    }

    private fun showLadder(id: String) {
        val frag = LeagueFragment()
        frag.arguments = Bundle().apply { putString(KEY_LEAGUE_ID, id) }
        activity?.let {
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, frag, id)
                .addToBackStack(null) // FIXME backstack busted
                .commit()
        }
    }
}