package com.example.footballer.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.footballer.data.DataSource.leagues
import com.example.footballer.data.DataSource.leaguesShort
import com.example.footballer.model.Fixture
import com.example.footballer.model.League
import com.example.footballer.model.Table
import com.example.footballer.model.TeamInfo
import com.example.footballer.network.APIService
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface FootballRepository {
    suspend fun getFixtures(): List<Fixture>
    suspend fun getLeagues(): List<League>
    suspend fun getLeagueTable(id: Int): Table

    suspend fun getTeamInfo(id: Int): TeamInfo
}

class NetworkFootballRepository(
    private val apiService: APIService,
): FootballRepository {
    //get upcoming fixtures for home screen
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getFixtures(): List<Fixture> {
        val dates = getDates()
        val compString = leaguesShort.joinToString(",")

        val response = apiService.getFixtures(
            dateFrom = dates.first,
            dateTo = dates.second,
            competitions = compString
        )

        return response.matches
    }
    //get leagues for home screen
    override suspend fun getLeagues(): List<League>{
        val response = apiService.getLeagues()
        val filteredLeagues = response.competitions.filter { league ->
            league.name in leagues
        }
        return filteredLeagues
    }

    //get table of specific league for table screen
    override suspend fun getLeagueTable(id: Int): Table {
        val response = apiService.getLeagueTable(id, "2023")
        //return first table of response to get Total of league points
        return response.standings[0]
    }

    override suspend fun getTeamInfo(id: Int): TeamInfo {
        return apiService.getTeamInfo(id)
    }

    //get today and tomorrows data
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDates(): Pair<String, String> {
        val currentDate = LocalDate.now()
        val tomorrowDate = currentDate.plusDays(1)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedCurrentDate = currentDate.format(formatter)
        val formattedTomorrowDate = tomorrowDate.format(formatter)
        return Pair(formattedCurrentDate, formattedTomorrowDate)
    }
}