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
import com.example.footballer.ui.screens.fixture.FixtureOverview
import com.example.footballer.ui.screens.fixture.FixtureUiState
import org.junit.Rule
import org.junit.Test

class FixtureTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeFixtureUiState = FixtureUiState( fixture =
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
        )
    )

    //check if fixture gets displayed
    @Test
    fun fixtureScreen_verifyFixture() {
        composeTestRule.setContent {
            FixtureOverview(fixture = fakeFixtureUiState.fixture, onTeamClick = {})
        }

        composeTestRule.onNodeWithText(fakeFixtureUiState.fixture.status).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeFixtureUiState.fixture.homeTeam.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeFixtureUiState.fixture.awayTeam.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeFixtureUiState.fixture.comp.name).assertIsDisplayed()
    }
}