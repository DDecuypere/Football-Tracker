package com.example.footballer.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.footballer.model.LeagueTableRow
import com.example.footballer.model.Team
import com.example.footballer.ui.screens.home.LeagueRow
import com.example.footballer.ui.screens.table.ScrollableTable
import com.example.footballer.ui.screens.table.TableUiState
import org.junit.Rule
import org.junit.Test

class TableScreen {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeTableUiState = TableUiState(table = listOf(
        LeagueTableRow(position = 1, club = Team(id = 1, name = "dortmund", crest = ""), goals = 15, points = 20),
        LeagueTableRow(position = 2, club = Team(id = 2, name = "bayern", crest = ""), goals = 18, points = 17)
    ))

    //check if table gets displayed
    @Test
    fun tableScreen_verifyTable() {
        composeTestRule.setContent {
            ScrollableTable(
                rows = fakeTableUiState.table
            )
        }
        fakeTableUiState.table.forEach {
            composeTestRule.onNodeWithText(it.club.name).assertIsDisplayed()
            composeTestRule.onNodeWithText(it.points.toString()).assertIsDisplayed()
            composeTestRule.onNodeWithText(it.goals.toString()).assertIsDisplayed()
            composeTestRule.onNodeWithText(it.position.toString()).assertIsDisplayed()
        }
    }
}