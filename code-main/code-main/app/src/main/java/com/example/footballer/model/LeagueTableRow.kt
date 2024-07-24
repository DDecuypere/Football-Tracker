package com.example.footballer.model

import com.google.gson.annotations.SerializedName

data class LeagueTableRow(
    @SerializedName("position")
    val position: Int,
    @SerializedName("team")
    val club: Team,
    @SerializedName("points")
    val points: Int,
    @SerializedName("goalDifference")
    val goals: Int,
)

data class Table(
    @SerializedName("type")
    val type: String,
    @SerializedName("table")
    val table: List<LeagueTableRow>
)

data class StandingsResponse(
    @SerializedName("standings")
    val standings: List<Table>,
    @SerializedName("competition")
    val comp: League
)