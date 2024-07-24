package com.example.footballer

import com.example.footballer.model.FixtureConverter
import com.example.footballer.model.FullTime
import com.example.footballer.model.HalfTime
import com.example.footballer.model.League
import com.example.footballer.model.Score
import com.example.footballer.model.Team
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class FixtureConverterTest {
    private lateinit var fixtureConverter: FixtureConverter

    @Before
    fun setUp() {
        fixtureConverter = FixtureConverter()
    }

    @Test
    fun fromScoreToString() {
        val score = Score(winner = "", duration = "normal", halfTime = HalfTime(2, 1), fullTime = FullTime(3, 2))
        val jsonString = fixtureConverter.fromScoreToString(score)
        assertEquals("{\"winner\":\"\",\"duration\":\"normal\",\"fullTime\":{\"home\":3,\"away\":2},\"halfTime\":{\"home\":2,\"away\":1}}", jsonString)
    }

    @Test
    fun toScoreFromString() {
        val jsonString = "{\"winner\":\"\",\"duration\":\"normal\",\"halfTime\":{\"home\":2,\"away\":1},\"fullTime\":{\"home\":3,\"away\":2}}"
        val score = fixtureConverter.toScoreFromString(jsonString)
        assertEquals(Score(winner = "", duration = "normal", halfTime = HalfTime(2, 1), fullTime = FullTime(3, 2)), score)
    }

    @Test
    fun fromFullTimeToString() {
        val fullTime = FullTime(home = 3, away = 2)
        val jsonString = fixtureConverter.fromFullTimeToString(fullTime)
        assertEquals("3,2", jsonString)
    }

    @Test
    fun toFullTimeFromString() {
        val jsonString = "3,2"
        val fullTime = fixtureConverter.toFullTimeFromString(jsonString)
        assertEquals(FullTime(home = 3, away = 2), fullTime)
    }

    @Test
    fun fromHalfTimeToString() {
        val halfTime = HalfTime(home = 2, away = 1)
        val jsonString = fixtureConverter.fromHalfTimeToString(halfTime)
        assertEquals("2,1", jsonString)
    }

    @Test
    fun toHalfTimeFromString() {
        val jsonString = "2,1"
        val halfTime = fixtureConverter.toHalfTimeFromString(jsonString)
        assertEquals(HalfTime(home = 2, away = 1), halfTime)
    }

    @Test
    fun toLeagueFromString() {
        val jsonString = "{\"id\":1,\"name\":\"Premier League\"}"
        val league = fixtureConverter.toLeagueFromString(jsonString)
        assertEquals(League(id = 1, name = "Premier League"), league)
    }

    @Test
    fun fromLeagueToString() {
        val league = League(id = 1, name = "Premier League")
        val jsonString = fixtureConverter.fromLeagueToString(league)
        assertEquals("{\"id\":1,\"name\":\"Premier League\"}", jsonString)
    }

    @Test
    fun toTeamFromString() {
        val jsonString = "{\"id\":1,\"name\":\"Manchester United\",\"crest\":\"\"}"
        val team = fixtureConverter.toTeamFromString(jsonString)
        assertEquals(Team(id = 1, name = "Manchester United", crest = ""), team)
    }

    @Test
    fun fromTeamToString() {
        val team = Team(id = 1, name = "Manchester United", crest = "")
        val jsonString = fixtureConverter.fromTeamToString(team)
        assertEquals("{\"id\":1,\"name\":\"Manchester United\",\"crest\":\"\"}", jsonString)
    }
}