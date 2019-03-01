package com.poe.leagueviewer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.poe.leagueviewer.data.LeagueRepository
import com.poe.leagueviewer.model.Ladder
import com.poe.leagueviewer.model.League
import com.poe.leagueviewer.model.LeagueMetaData

class LeagueViewModel(private val repository: LeagueRepository) : ViewModel() {

    fun getLeague(id: String): LiveData<League> {
        return repository.getLeague(id)
    }

    fun getLeagues(type: String): LiveData<List<LeagueMetaData>> {
        return repository.getLeagues(type)
    }

    fun getLadder(id: String): LiveData<PagedList<Ladder>> {
        return repository.getLadders(id)
    }
}