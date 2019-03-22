package com.poe.leagueviewer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.poe.leagueviewer.viewmodels.LeagueViewModel
import com.poe.leagueviewer.R
import com.poe.leagueviewer.adapter.LeagueListAdapter
import com.poe.leagueviewer.model.LeagueMetaData
import com.poe.leagueviewer.utils.KEY_LEAGUE_ID
import com.poe.leagueviewer.utils.KEY_TYPE
import com.poe.leagueviewer.utils.UrlUtil
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class LeagueListFragment : Fragment() {

    private val viewModel by sharedViewModel<LeagueViewModel>()
    private val urlUtil by inject<UrlUtil>()

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.title_leagues)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_league_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = LeagueListAdapter({ id -> showLadder(id) }, { url ->
            context?.let { ctx ->
                urlUtil.loadPage(ctx, url)
            }
        })
        view.findViewById<RecyclerView>(R.id.list_leagues).adapter = adapter
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
            ?.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
            ?.replace(R.id.fragment_container, frag, id)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun showContent(view: View) {
        view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
        view.findViewById<RecyclerView>(R.id.list_leagues).visibility = View.VISIBLE
    }
}