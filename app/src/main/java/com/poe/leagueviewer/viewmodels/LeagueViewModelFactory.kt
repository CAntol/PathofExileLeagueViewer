package com.poe.leagueviewer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.poe.leagueviewer.data.LeagueRepository

class LeagueViewModelFactory(private val repository: LeagueRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LeagueViewModel(repository) as T
    }
}