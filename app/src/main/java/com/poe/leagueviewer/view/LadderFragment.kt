package com.poe.leagueviewer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.poe.leagueviewer.R
import com.poe.leagueviewer.adapter.LadderListAdapter
import com.poe.leagueviewer.model.Ladder
import com.poe.leagueviewer.utils.KEY_LEAGUE_ID
import com.poe.leagueviewer.utils.UrlUtil
import com.poe.leagueviewer.viewmodels.LeagueViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LadderFragment : Fragment() {

    private val viewModel: LeagueViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getString(KEY_LEAGUE_ID)?.let {
            (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.title_ladder, it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_league, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = LadderListAdapter { ladder ->
            context?.let { ctx ->
                UrlUtil.instance.loadCharacterPage(ctx, ladder)
            }
        }
        view.findViewById<RecyclerView>(R.id.list_ladder).adapter = adapter
        subscribeUi(view, adapter)
    }

    private fun subscribeUi(view: View, adapter: LadderListAdapter) {
        arguments?.getString(KEY_LEAGUE_ID)?.let {
            viewModel.getLadder(it).observe(this, Observer<PagedList<Ladder>> { ladders ->
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