package com.poe.leagueviewer.utils

import com.poe.leagueviewer.data.PoeApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    companion object {
        val instance: PoeApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://api.pathofexile.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(PoeApi::class.java)
        }
    }
}