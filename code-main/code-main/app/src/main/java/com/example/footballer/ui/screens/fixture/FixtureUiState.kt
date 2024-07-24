package com.example.footballer.ui.screens.fixture

import com.example.footballer.model.Fixture
import com.example.footballer.model.FullTime
import com.example.footballer.model.HalfTime
import com.example.footballer.model.League
import com.example.footballer.model.Score
import com.example.footballer.model.Team

data class FixtureUiState(
    val fixture: Fixture = emptyFixture()
)

private fun emptyFixture() : Fixture{
    return Fixture(
        id = 0,
        comp = League(id = 0, name = ""),
        homeTeam = Team(id = 0, name = "", crest = ""),
        awayTeam = Team(id = 0, name = "", crest = ""),
        status = "",
        score = Score(winner = null, duration = "", halfTime = HalfTime(home = null, away = null), fullTime = FullTime(home = null, away = null)),
        dateUtc = ""
    )
}
