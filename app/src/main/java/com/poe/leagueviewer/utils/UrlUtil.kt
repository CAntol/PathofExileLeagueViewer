package com.poe.leagueviewer.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.poe.leagueviewer.R
import com.poe.leagueviewer.model.Ladder

private const val URL_CHAR = "https://www.pathofexile.com/account/view-profile/%s/characters?characterName=%s"

class UrlUtil {

    fun loadCharacterPage(context: Context, ladder: Ladder) {
        val accountName = ladder.account?.name
        val charName = ladder.character?.name
        if (!accountName.isNullOrEmpty() && !charName.isNullOrEmpty()) {
            val url = String.format(URL_CHAR, accountName, charName)
            loadPage(context, url)
        }
    }

    fun loadPage(context: Context, url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(context.getColor(R.color.colorPrimary))
        builder.build().launchUrl(context, Uri.parse(url))
    }

    companion object {
        val instance: UrlUtil by lazy {
            UrlUtil()
        }
    }
}