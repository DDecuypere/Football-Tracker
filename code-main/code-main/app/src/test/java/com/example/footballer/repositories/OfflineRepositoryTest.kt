package com.example.footballer.repositories

import com.example.footballer.data.NetworkFootballRepository
import com.example.footballer.data.OfflineFixtureRepository
import com.example.footballer.data.OfflineRepository
import com.example.footballer.fake.FakeApiService
import com.example.footballer.fake.FakeDataSource
import com.example.footballer.fake.FakeFixtureDao
import com.example.footballer.model.Fixture
import com.example.footballer.model.FullTime
import com.example.footballer.model.HalfTime
import com.example.footballer.model.League
import com.example.footballer.model.Score
import com.example.footballer.model.Team
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class OfflineRepositoryTest {
    @Before
    fun setUp() {
        //initialisation of the fixtures list before every test
        FakeDataSource.currentFixtures = mutableListOf(
            Fixture(
                id = 1,
                comp = League(id = 1, name = "Bundesliga"),
                homeTeam = Team(id = 1, name = "dortmund", crest = ""),
                awayTeam = Team(id = 2, name = "bayern", crest = ""),
                status = "finished",
                dateUtc = "2024-03-07",
                score = Score(
                    winner = null,
                    duration = "",
                    halfTime = HalfTime(1, 2),
                    fullTime = FullTime(1, 1)
                )
            ),
            Fixture(
                id = 2,
                comp = League(id = 1, name = "Bundesliga"),
                homeTeam = Team(id = 2, name = "bayer", crest = ""),
                awayTeam = Team(id = 1, name = "dortmund", crest = ""),
                status = "timed",
                dateUtc = "2024-03-02",
                score = Score(
                    winner = null,
                    duration = "",
                    halfTime = HalfTime(null, null),
                    fullTime = FullTime(null, null)
                )
            )
        )
    }
    @Test
    fun `get all fixtures offline, check correct list of fixtures` () =
        runTest {
            val repository = OfflineRepository(
                fixtureDAO = FakeFixtureDao()
            )
            val actualFixtures = repository.getOfflineFixtures().first()
            Assert.assertEquals(FakeDataSource.currentFixtures, actualFixtures)
        }

    @Test
    fun `get fixture offline, check correct fixture` () =
        runTest {
            val repository = OfflineRepository(
                fixtureDAO = FakeFixtureDao()
            )
            val actualFixture = repository.getOfflineFixture(1).first()
            Assert.assertEquals(FakeDataSource.currentFixtures[0], actualFixture)
        }

    @Test
    fun `insert fixture offline, check correct insert of fixture` () =
        runTest {
            val repository = OfflineRepository(
                fixtureDAO = FakeFixtureDao()
            )
            repository.insertFixture(
                Fixture(
                    id = 3,
                    comp = League(id = 1, name = "Bundesliga"),
                    homeTeam = Team(id = 2, name = "bayer", crest = ""),
                    awayTeam = Team(id = 1, name = "dortmund", crest = ""),
                    status = "finished",
                    dateUtc = "2024-03-04",
                    score = Score(
                        winner = null,
                        duration = "",
                        halfTime = HalfTime(1, 0),
                        fullTime = FullTime(null, null)
                    )
                )
            )
            val actualFixtures = repository.getOfflineFixtures().first()

            Assert.assertEquals(
                mutableListOf(
                    Fixture(
                        id = 1,
                        comp = League(id = 1, name = "Bundesliga"),
                        homeTeam = Team(id = 1, name = "dortmund", crest = ""),
                        awayTeam = Team(id = 2, name = "bayern", crest = ""),
                        status = "finished",
                        dateUtc = "2024-03-07",
                        score = Score(
                            winner = null,
                            duration = "",
                            halfTime = HalfTime(1, 2),
                            fullTime = FullTime(1, 1)
                        )
                    ),
                    Fixture(
                        id = 2,
                        comp = League(id = 1, name = "Bundesliga"),
                        homeTeam = Team(id = 2, name = "bayer", crest = ""),
                        awayTeam = Team(id = 1, name = "dortmund", crest = ""),
                        status = "timed",
                        dateUtc = "2024-03-02",
                        score = Score(
                            winner = null,
                            duration = "",
                            halfTime = HalfTime(null, null),
                            fullTime = FullTime(null, null)
                        )
                    ),
                    Fixture(
                        id = 3,
                        comp = League(id = 1, name = "Bundesliga"),
                        homeTeam = Team(id = 2, name = "bayer", crest = ""),
                        awayTeam = Team(id = 1, name = "dortmund", crest = ""),
                        status = "finished",
                        dateUtc = "2024-03-04",
                        score = Score(
                            winner = null,
                            duration = "",
                            halfTime = HalfTime(1, 0),
                            fullTime = FullTime(null, null)
                        )
                    )
                ),
                actualFixtures
            )
        }

    @Test
    fun `delete fixture offline, check correct removal of fixture` () =
        runTest {
            val repository = OfflineRepository(
                fixtureDAO = FakeFixtureDao()
            )
            repository.deleteOldFixture("2024-03-05")
            val actualFixtures = repository.getOfflineFixtures().first()

            Assert.assertEquals(
                mutableListOf(
                    Fixture(
                        id = 1,
                        comp = League(id = 1, name = "Bundesliga"),
                        homeTeam = Team(id = 1, name = "dortmund", crest = ""),
                        awayTeam = Team(id = 2, name = "bayern", crest = ""),
                        status = "finished",
                        dateUtc = "2024-03-07",
                        score = Score(
                            winner = null,
                            duration = "",
                            halfTime = HalfTime(1, 2),
                            fullTime = FullTime(1, 1)
                        )
                    )
                ),
                actualFixtures
            )
        }

    @Test
    fun `update fixture, check correct updated fixture` () =
        runTest {
            val repository = OfflineRepository(
                fixtureDAO = FakeFixtureDao()
            )
            repository.updateFixture(
                Fixture(
                    id = 1,
                    comp = League(id = 1, name = "Bundesliga"),
                    homeTeam = Team(id = 1, name = "dortmund", crest = ""),
                    awayTeam = Team(id = 2, name = "bayern", crest = ""),
                    status = "timed",
                    dateUtc = "2024-03-07",
                    score = Score(
                        winner = null,
                        duration = "",
                        halfTime = HalfTime(1, 2),
                        fullTime = FullTime(1, 1)
                    )
                )
            )
            val actualFixtures = repository.getOfflineFixtures().first()

            Assert.assertEquals(
                mutableListOf(
                    Fixture(
                        id = 1,
                        comp = League(id = 1, name = "Bundesliga"),
                        homeTeam = Team(id = 1, name = "dortmund", crest = ""),
                        awayTeam = Team(id = 2, name = "bayern", crest = ""),
                        status = "timed",
                        dateUtc = "2024-03-07",
                        score = Score(
                            winner = null,
                            duration = "",
                            halfTime = HalfTime(1, 2),
                            fullTime = FullTime(1, 1)
                        )
                    ),
                    Fixture(
                        id = 2,
                        comp = League(id = 1, name = "Bundesliga"),
                        homeTeam = Team(id = 2, name = "bayer", crest = ""),
                        awayTeam = Team(id = 1, name = "dortmund", crest = ""),
                        status = "timed",
                        dateUtc = "2024-03-02",
                        score = Score(
                            winner = null,
                            duration = "",
                            halfTime = HalfTime(null, null),
                            fullTime = FullTime(null, null)
                        )
                    )
                ),
                actualFixtures
            )
        }
}