package com.poe.leagueviewer.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.poe.leagueviewer.model.Ladder
import com.poe.leagueviewer.model.League
import com.poe.leagueviewer.model.LeagueMetaData
import com.poe.leagueviewer.utils.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeagueRepository(private val poeApi: PoeApi) {

    // TODO dumb implementation, make smarter
    private val leaguesCache = mutableMapOf<String, LiveData<List<LeagueMetaData>>>()
    private val leagueCache = mutableMapOf<String, LiveData<League>>()
    private val ladderCache = mutableMapOf<String, LiveData<PagedList<Ladder>>>()

    fun getLeagues(type: String): LiveData<List<LeagueMetaData>> {
        leaguesCache[type]?.let {
            return it
        }

        val data = MutableLiveData<List<LeagueMetaData>>()
        leaguesCache[type] = data
        poeApi.leagueList(type).enqueue(object : Callback<List<LeagueMetaData>> {
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
        leagueCache[id]?.let {
            return it
        }

        val data = MutableLiveData<League>()
        leagueCache[id] = data
        poeApi.league(id).enqueue(object : Callback<League> {
            override fun onFailure(call: Call<League>, t: Throwable) {
                Log.e("LeagueRepo", t.message)
            }
            override fun onResponse(call: Call<League>, response: Response<League>) {
                data.value = response.body()
            }
        })
        return data
    }

    fun getLadders(id: String): LiveData<PagedList<Ladder>> {
        ladderCache[id]?.let {
            return it
        }

        val data = LadderDataSourceFactory(poeApi, id).toLiveData(20)
        ladderCache[id] = data
        return data
    }

    companion object {
        fun getInstance(): LeagueRepository {
            return LeagueRepository(RetrofitFactory.getInstance().create(PoeApi::class.java))
        }
    }
}