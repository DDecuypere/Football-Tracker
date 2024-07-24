package com.example.footballer.app

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.footballer.assertCurrentRouteName
import com.example.footballer.rules.onNodeWithStringId
import com.example.footballer.ui.FootballerNavHost
import com.example.footballer.ui.screens.fixture.FixtureDestination
import com.example.footballer.ui.screens.home.HomeDestination
import com.example.footballer.R
import com.example.footballer.ui.NavViewModel
import com.example.footballer.ui.screens.table.TableDestination
import com.example.footballer.ui.screens.team.TeamDestination
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            FootballerNavHost(navController = navController)
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(HomeDestination.route)
    }

    @Test
    fun testNavigationToTableScreenWithButton() {
        val nodes = composeTestRule.onAllNodesWithTag("Table").fetchSemanticsNodes()
        nodes.forEach { node ->
            composeTestRule.runOnUiThread {
                node.config[SemanticsActions.OnClick].action?.invoke()
            }
            navController.assertCurrentRouteName(TableDestination.routeWithArgs)
        }
    }

    @Test
    fun testNavigationToTableScreenWithNav() {
        composeTestRule.onNodeWithStringId(R.string.league).performClick()
        navController.assertCurrentRouteName(TableDestination.routeWithArgs)
    }

    /*problem finding the tags created in app code*/

    /*@Test
    fun testNavigationToFixtureScreen() {
        navigateFixture()
        navController.assertCurrentRouteName(FixtureDestination.routeWithArgs)
    }

    @Test
    fun testNavigationToTeamScreen() {
        navigateFixture()
        composeTestRule.onAllNodesWithTag("TeamCrest")
            .onFirst()
            .performClick()
        navController.assertCurrentRouteName(TeamDestination.routeWithArgs)
    }

    @Test
    fun testNavigationToHomeScreen() {
        composeTestRule.onNodeWithStringId(R.string.app_name).performClick()
        navController.assertCurrentRouteName(HomeDestination.route)
    }

    private fun navigateFixture(){
        composeTestRule.onAllNodesWithTag("Fixture")
            .onFirst()
            .performClick()
    }*/
}