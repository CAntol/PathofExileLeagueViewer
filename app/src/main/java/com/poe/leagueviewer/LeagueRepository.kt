package com.poe.leagueviewer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.poe.leagueviewer.model.League
import com.poe.leagueviewer.model.LeagueMetaData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeagueRepository(private val webservice: Webservice) {

    fun getLeagues(type: String): LiveData<List<LeagueMetaData>> {
        val data = MutableLiveData<List<LeagueMetaData>>()
        webservice.leagueList(type).enqueue(object : Callback<List<LeagueMetaData>> {
            override fun onFailure(call: Call<List<LeagueMetaData>>, t: Throwable) {
                Log.e("LeagueRepo", t.message)
            }
            override fun onResponse(call: Call<List<LeagueMetaData>>, response: Response<List<LeagueMetaData>>) {
                data.value = response.body()
            }
        })
        return data
    }

    fun getLeague(id: String): LiveData<League> {
        val data = MutableLiveData<League>()
        webservice.league(id).enqueue(object : Callback<League> {
            override fun onFailure(call: Call<League>, t: Throwable) {
                Log.e("LeagueRepo", t.message)
            }
            override fun onResponse(call: Call<League>, response: Response<League>) {
                data.value = response.body()
            }
        })
        return data
    }
}