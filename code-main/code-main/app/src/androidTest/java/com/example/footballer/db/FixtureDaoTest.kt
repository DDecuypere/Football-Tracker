package com.example.footballer.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.footballer.data.FixtureDAO
import com.example.footballer.data.FootballerDatabase
import com.example.footballer.model.Fixture
import com.example.footballer.model.FullTime
import com.example.footballer.model.HalfTime
import com.example.footballer.model.League
import com.example.footballer.model.Score
import com.example.footballer.model.Team
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FixtureDaoTest {
    private lateinit var fixtureDAO: FixtureDAO
    private lateinit var footballerDatabase: FootballerDatabase
    private val fixture1 = Fixture(
        id = 1,
        comp = League(id = 1, name = "bundesliga"),
        homeTeam = Team(id = 1, name = "Dortmund", crest = ""),
        awayTeam = Team(id = 2, name = "Bayern", crest = ""),
        status = "finished",
        dateUtc = "",
        score = Score(
            winner = null,
            duration = "",
            halfTime = HalfTime(1, 2),
            fullTime = FullTime(1, 1)
        )
    )
    private val fixture2 = Fixture(
        id = 2,
        comp = League(id = 1, name = "bundesliga"),
        homeTeam = Team(id = 2, name = "bayer", crest = ""),
        awayTeam = Team(id = 1, name = "dortmund", crest = ""),
        status = "timed",
        dateUtc = "2024-03-02",
        score = Score(
            winner = null,
            duration = "",
            halfTime = HalfTime(0, 0),
            fullTime = FullTime(0, 0)
        )
    )

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        footballerDatabase = Room.inMemoryDatabaseBuilder(context, FootballerDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        fixtureDAO = footballerDatabase.fixtureDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        footballerDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsFixtureIntoDB() = runBlocking {
        addOneFixtureToDb()
        val allFixtures = fixtureDAO.getCurrentFixtures().first()
        assertEquals(allFixtures[0], fixture1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllFixtures_returnsAllFixturesFromDB() = runBlocking {
        addTwoFixturesToDb()
        val allFixtures = fixtureDAO.getCurrentFixtures().first()
        assertEquals(allFixtures[0], fixture1)
        assertEquals(allFixtures[1], fixture2)
    }


    @Test
    @Throws(Exception::class)
    fun daoGetFixture_returnsFixtureFromDB() = runBlocking {
        addOneFixtureToDb()
        val fixture = fixtureDAO.getFixture(1)
        assertEquals(fixture.first(), fixture1)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteFixtures_deletesFixturesFromDB() = runBlocking {
        addTwoFixturesToDb()
        fixtureDAO.delete("2024-03-05")
        val allFixtures = fixtureDAO.getCurrentFixtures().first()
        assertTrue(allFixtures.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateFixture_updatesFixtureInDB() = runBlocking {
        addTwoFixturesToDb()
        fixtureDAO.update(Fixture(
            id = 2,
            comp = League(id = 1, name = "bundesliga"),
            homeTeam = Team(id = 2, name = "bayer", crest = ""),
            awayTeam = Team(id = 1, name = "dortmund", crest = ""),
            status = "finished",
            dateUtc = "2024-03-02",
            score = Score(
                winner = null,
                duration = "",
                halfTime = HalfTime(0, 0),
                fullTime = FullTime(0, 0)
            )
        ))
        val allItems = fixtureDAO.getCurrentFixtures().first()
        Assert.assertEquals(allItems[1], Fixture(
            id = 2,
            comp = League(id = 1, name = "bundesliga"),
            homeTeam = Team(id = 2, name = "bayer", crest = ""),
            awayTeam = Team(id = 1, name = "dortmund", crest = ""),
            status = "finished",
            dateUtc = "2024-03-02",
            score = Score(
                winner = null,
                duration = "",
                halfTime = HalfTime(0, 0),
                fullTime = FullTime(0, 0)
            )
        ))
    }

    private suspend fun addOneFixtureToDb() {
        fixtureDAO.insert(fixture1)
    }

    private suspend fun addTwoFixturesToDb() {
        fixtureDAO.insert(fixture1)
        fixtureDAO.insert(fixture2)
    }
}