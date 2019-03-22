package com.poe.leagueviewer

import android.app.Application
import com.poe.leagueviewer.data.LeagueRepository
import com.poe.leagueviewer.data.PoeApi
import com.poe.leagueviewer.viewmodels.LeagueViewModel
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.startKoin
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainApplication: Application() {

    // Dependency Injection module
    private val diModule = module {

        // PoeApi instance
        single { Retrofit.Builder()
            .baseUrl("http://api.pathofexile.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PoeApi::class.java)}
        // Repo
        single { LeagueRepository(get()) }
        // ViewModel
        viewModel { LeagueViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        startKoin(this, listOf(diModule))
    }
}