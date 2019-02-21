package com.poe.leagueviewer.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    companion object {
        private var instance: Retrofit? = null
        fun getInstance(): Retrofit {
            return instance ?:
            Retrofit.Builder()
                .baseUrl("http://api.pathofexile.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}