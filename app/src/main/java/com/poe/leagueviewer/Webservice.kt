package com.poe.leagueviewer

import com.poe.leagueviewer.model.League
import com.poe.leagueviewer.model.LeagueMetaData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Webservice {

    @GET("/leagues")
    fun leagueList(@Query("type") type: String): Call<List<LeagueMetaData>>

    @GET("/leagues/{id}")
    fun league(@Path("id") id: String): Call<League>
}