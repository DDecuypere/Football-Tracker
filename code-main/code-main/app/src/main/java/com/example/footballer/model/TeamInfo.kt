package com.example.footballer.model

import com.google.gson.annotations.SerializedName

data class TeamInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("crest")
    val crest: String,
    @SerializedName("founded")
    val founded: String,
    @SerializedName("venue")
    val venue: String,
    @SerializedName("squad")
    val squad: List<Player>,
)

data class Player(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("position")
    val position: String,
)
