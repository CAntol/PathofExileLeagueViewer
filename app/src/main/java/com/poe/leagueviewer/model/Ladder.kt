package com.poe.leagueviewer.model

import com.google.gson.annotations.SerializedName

data class Ladder(val rank: Int?,
                  val dead: Boolean?,
                  val online: Boolean?,
                  val character: Character?,
                  val account: Account?) {
    inner class Character(val name: String?, val level: Int?, @SerializedName("class") val poeClass: String?, val experience: Long?)
    inner class Account(val name: String?, val challenges: Challenges?, val twitch: Twitch?)
    inner class Challenges(val total: Int?)
    inner class Twitch(val name: String?)
}