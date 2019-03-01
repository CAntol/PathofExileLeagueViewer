package com.poe.leagueviewer.data

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.poe.leagueviewer.model.Ladder
import com.poe.leagueviewer.model.LadderObject
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

// TODO handle retries
class LadderDataSource(private val poeApi: PoeApi, private val id: String) : PageKeyedDataSource<String, Ladder>() {

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Ladder>) {
        val request = poeApi.ladders(id, 0.toString())

        try {
            val response = request.execute()
            val ladderList = response.body()?.entries ?: emptyList()
            callback.onResult(ladderList, null, ladderList.size.toString())
        } catch (ioException: IOException) {
            Log.e("LadderDataSource", ioException.message)
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Ladder>) {
        // no-op
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Ladder>) {
        poeApi.ladders(id, params.key).enqueue(object : retrofit2.Callback<LadderObject> {
            override fun onFailure(call: Call<LadderObject>, t: Throwable) {
                Log.e("LadderDataSource", t.message)
            }

            override fun onResponse(call: Call<LadderObject>, response: Response<LadderObject>) {
                if (response.isSuccessful) {
                    val ladderList = response.body()?.entries ?: emptyList()
                    val next = if (ladderList.isNotEmpty()) (params.key.toLong() + ladderList.size).toString() else null
                    callback.onResult(ladderList, next)
                } else {
                   Log.e("LadderDataSource", "Ladder request unsuccessful")
                }
            }
        })
    }
}