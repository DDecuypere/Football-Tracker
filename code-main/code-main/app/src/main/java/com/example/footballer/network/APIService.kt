package com.example.footballer.network

import com.example.footballer.model.FixturesResponse
import com.example.footballer.model.LeaguesResponse
import com.example.footballer.model.StandingsResponse
import com.example.footballer.model.TeamInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {
    @GET("matches")
    suspend fun getFixtures(
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String,
        @Query("competitions") competitions: String
    ): FixturesResponse

    @GET("competitions")
    suspend fun getLeagues(): LeaguesResponse

    @GET("competitions/{id}/standings")
    suspend fun getLeagueTable(
        @Path("id") id: Int,
        @Query("season") season: String
    ): StandingsResponse

    @GET("teams/{id}")
    suspend fun getTeamInfo(
        @Path("id") id: Int,
    ): TeamInfo
}


