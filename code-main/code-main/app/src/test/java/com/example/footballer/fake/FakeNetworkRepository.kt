package com.example.footballer.fake

import com.example.footballer.data.FootballRepository
import com.example.footballer.model.Fixture
import com.example.footballer.model.League
import com.example.footballer.model.Table
import com.example.footballer.model.TeamInfo

class FakeNetworkRepository: FootballRepository {
    override suspend fun getFixtures(): List<Fixture> {
        return FakeDataSource.fixtures
    }

    override suspend fun getLeagues(): List<League> {
        return FakeDataSource.leagues
    }

    override suspend fun getLeagueTable(id: Int): Table {
        return Table("full", FakeDataSource.leagueTable)
    }

    override suspend fun getTeamInfo(id: Int): TeamInfo {
        return FakeDataSource.teamInfo
    }
}