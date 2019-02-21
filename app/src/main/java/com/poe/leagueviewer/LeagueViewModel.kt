package com.poe.leagueviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.poe.leagueviewer.model.LeagueMetaData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LeagueViewModel : ViewModel() {
    private val repository: LeagueRepository

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.pathofexile.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        repository = LeagueRepository(retrofit.create(Webservice::class.java))
    }

    fun getLeagues(type: String): LiveData<List<LeagueMetaData>> {
        return repository.getLeagues(type)
    }
}