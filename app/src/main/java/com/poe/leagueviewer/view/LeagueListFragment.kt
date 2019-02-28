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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle()

        activity?.supportFragmentManager?.let { fm ->
            fm.addOnBackStackChangedListener {
                if (fm.backStackEntryCount == 0) {
                    setTitle()
                }
            }
        }
    }

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
        subscribeUi(view, adapter)
    }

    private fun subscribeUi(view: View, adapter: LeagueListAdapter) {
        arguments?.getString(KEY_TYPE)?.let {
            viewModel.getLeagues(it).observe(this, Observer<List<LeagueMetaData>> { data ->
                showContent(view)
                adapter.submitList(data)
            })
        }
    }

    private fun showLadder(id: String) {
        val frag = LadderFragment()
        frag.arguments = Bundle().apply { putString(KEY_LEAGUE_ID, id) }
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, frag, id)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun showContent(view: View) {
        view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
        view.findViewById<RecyclerView>(R.id.list_leagues).visibility = View.VISIBLE
    }

    private fun setTitle() {
        activity?.actionBar?.title = getString(R.string.title_leagues)
    }
}