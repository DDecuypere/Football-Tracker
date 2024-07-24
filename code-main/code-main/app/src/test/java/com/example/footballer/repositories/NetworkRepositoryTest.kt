package com.example.footballer.repositories

import com.example.footballer.data.NetworkFootballRepository
import com.example.footballer.fake.FakeApiService
import com.example.footballer.fake.FakeDataSource
import com.example.footballer.model.League
import com.example.footballer.model.StandingsResponse
import com.example.footballer.model.Table
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.stub

class NetworkRepositoryTest {
    @Test
    fun `get all fixtures, check correct list of fixtures` ()  =
        runTest {
            val repository = NetworkFootballRepository(
                 apiService = FakeApiService()
            )
            Assert.assertEquals(FakeDataSource.fixtures, repository.getFixtures())
        }

    @Test
    fun `get all leagues, check correct list of leagues` () =
        runTest {
            val repository = NetworkFootballRepository(
                apiService = FakeApiService()
            )
            Assert.assertEquals(FakeDataSource.leagues, repository.getLeagues())
        }

    @Test
    fun `get a league table, check correct table` () =
        runTest {
            val repository = NetworkFootballRepository(
                apiService = FakeApiService()
            )
            Assert.assertEquals(FakeDataSource.leagueTable, repository.getLeagueTable(1).table)
        }

    @Test
    fun `get team info, check correct team` () =
        runTest {
            val repository = NetworkFootballRepository(
                apiService = FakeApiService()
            )
            Assert.assertEquals(FakeDataSource.teamInfo, repository.getTeamInfo(1))
        }
}