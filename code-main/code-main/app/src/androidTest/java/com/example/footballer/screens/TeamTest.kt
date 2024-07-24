package com.example.footballer.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.footballer.model.Player
import com.example.footballer.model.TeamInfo
import com.example.footballer.ui.screens.team.TeamInfoBox
import com.example.footballer.ui.screens.team.TeamRepresentation
import com.example.footballer.ui.screens.team.TeamSquad
import com.example.footballer.ui.screens.team.TeamUiState
import org.junit.Rule
import org.junit.Test

class TeamTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeTeamUiState = TeamUiState( team =
        TeamInfo(id = 1, name = "Dortmund", crest = "", founded = "1900", venue = "park", squad = listOf(
            Player(id = 1, name = "donald", position = "defender")
        ))
    )

    //check if team details get displayed
    @Test
    fun teamScreen_verifyDetails() {
        composeTestRule.setContent {
            TeamRepresentation(team = fakeTeamUiState.team)
        }

        composeTestRule.onNodeWithText(fakeTeamUiState.team.name).assertIsDisplayed()
    }

    //check if team info get displayed
    @Test
    fun teamScreen_verifyInfo() {
        composeTestRule.setContent {
            TeamInfoBox(team = fakeTeamUiState.team)
        }

        composeTestRule.onNodeWithText(fakeTeamUiState.team.founded).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeTeamUiState.team.venue).assertIsDisplayed()
    }

    //check if team squad get displayed
    @Test
    fun teamScreen_verifySquad() {
        composeTestRule.setContent {
            TeamSquad(squad = fakeTeamUiState.team.squad)
        }

        fakeTeamUiState.team.squad.forEach{
            composeTestRule.onNodeWithText(it.name).assertIsDisplayed()
            composeTestRule.onNodeWithText(it.position).assertIsDisplayed()
        }
    }
}