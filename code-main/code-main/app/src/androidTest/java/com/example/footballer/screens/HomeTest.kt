package com.example.footballer.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.footballer.model.Fixture
import com.example.footballer.model.FullTime
import com.example.footballer.model.HalfTime
import com.example.footballer.model.League
import com.example.footballer.model.Score
import com.example.footballer.model.Team
import com.example.footballer.ui.screens.home.FixtureCard
import com.example.footballer.ui.screens.home.HomeUiState
import com.example.footballer.ui.screens.home.LeagueRow
import org.junit.Rule
import org.junit.Test

class HomeTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val leagues = listOf(League(1, "Bundesliga"), League(2, "Premier League"))
    private val fixtures = listOf(
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
            fullTime = FullTime(2, 3)
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

    private val fakeHomeUiState = HomeUiState(
        leagues = leagues,
        fixtures = fixtures
    )


    //check if leagues get displayed
    @Test
    fun homeScreen_verifyLeagues() {
        composeTestRule.setContent {
            LeagueRow(
                onLeagueClick = {},
                leagues = fakeHomeUiState.leagues
            )
        }
        leagues.forEach {
            composeTestRule.onNodeWithText(it.name).assertIsDisplayed()
        }
    }

    //check if a single fixture gets displayed
    @Test
    fun homeScreen_verifyFixture() {
        composeTestRule.setContent {
            FixtureCard(
                fixture = fakeHomeUiState.fixtures[0],
                onFixtureClick = {}
            )
        }

        composeTestRule.onNodeWithText(fixtures[0].homeTeam.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fixtures[0].awayTeam.name).assertIsDisplayed()

        composeTestRule.onNodeWithText("2 - 3").assertIsDisplayed()
    }
}