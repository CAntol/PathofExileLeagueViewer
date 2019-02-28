package com.poe.leagueviewer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poe.leagueviewer.R
import com.poe.leagueviewer.adapter.LadderListAdapter
import com.poe.leagueviewer.data.LeagueRepository
import com.poe.leagueviewer.model.Ladder
import com.poe.leagueviewer.utils.KEY_LEAGUE_ID
import com.poe.leagueviewer.viewmodels.LeagueViewModel
import com.poe.leagueviewer.viewmodels.LeagueViewModelFactory
import java.lang.Exception

class LadderFragment : Fragment() {

    lateinit var viewModel: LeagueViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(KEY_LEAGUE_ID)?.let {
            activity?.actionBar?.title = getString(R.string.title_ladder, it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // TODO - DI
        val factory = LeagueViewModelFactory(LeagueRepository.getInstance())
        viewModel = activity?.run {
            ViewModelProviders.of(this, factory).get(LeagueViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        return inflater.inflate(R.layout.fragment_league, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = LadderListAdapter()
        view.findViewById<RecyclerView>(R.id.list_ladder).apply {
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation))
        }
        subscribeUi(view, adapter)
    }

    private fun subscribeUi(view: View, adapter: LadderListAdapter) {
        arguments?.getString(KEY_LEAGUE_ID)?.let {
            viewModel.getLadder(it).observe(this, Observer<List<Ladder>> { ladders ->
                showContent(view)
                adapter.submitList(ladders)
            })
        }
    }

    private fun showContent(view: View) {
        view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
        view.findViewById<RecyclerView>(R.id.list_ladder).visibility = View.VISIBLE
    }
}