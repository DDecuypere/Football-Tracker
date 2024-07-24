package com.example.footballer.fake

import com.example.footballer.model.FixturesResponse
import com.example.footballer.model.League
import com.example.footballer.model.LeaguesResponse
import com.example.footballer.model.Player
import com.example.footballer.model.StandingsResponse
import com.example.footballer.model.Table
import com.example.footballer.model.TeamInfo
import com.example.footballer.network.APIService
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class FakeApiService: APIService {
    override suspend fun getFixtures(
        dateFrom: String,
        dateTo: String,
        competitions: String
    ): FixturesResponse {
        val fixtures = FakeDataSource.fixtures
        return FixturesResponse(fixtures)
    }

    override suspend fun getLeagues(): LeaguesResponse {
        val leagues = FakeDataSource.leagues
        return LeaguesResponse(leagues)
    }

    override suspend fun getLeagueTable(id: Int, season: String): StandingsResponse {
        val table = FakeDataSource.leagueTable
        return StandingsResponse(listOf(Table("Full",table)), League(id = 1, name = "bundesliga") )
    }

    override suspend fun getTeamInfo(id: Int): TeamInfo {
        if (FakeDataSource.teamInfo.id == id){
            return FakeDataSource.teamInfo
        }
        return TeamInfo(0, "", "", "", "", listOf(Player(0, "", "")))
    }
}