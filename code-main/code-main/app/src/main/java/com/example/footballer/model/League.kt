package com.example.footballer.model

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow

data class League(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

data class LeaguesResponse(
    @SerializedName("competitions")
    val competitions: List<League>
)