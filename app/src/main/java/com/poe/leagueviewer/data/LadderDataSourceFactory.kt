package com.poe.leagueviewer.data

import androidx.paging.DataSource
import com.poe.leagueviewer.model.Ladder

class LadderDataSourceFactory(private val poeApi: PoeApi, private val id: String) : DataSource.Factory<String, Ladder>() {
    override fun create(): DataSource<String, Ladder> {
        return LadderDataSource(poeApi, id)
    }
}