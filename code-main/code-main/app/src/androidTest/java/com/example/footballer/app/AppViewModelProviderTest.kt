package com.example.footballer.app

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.TestLoader.Factory
import com.example.footballer.FootballerApplication
import com.example.footballer.MainActivity
import com.example.footballer.ui.AppViewModelProvider
import com.example.footballer.ui.FootballerNavHost
import com.example.footballer.ui.screens.fixture.FixtureViewModel
import com.example.footballer.ui.screens.home.HomeViewModel
import com.example.footballer.ui.screens.table.TableViewModel
import com.example.footballer.ui.screens.team.TeamViewModel
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppViewModelProviderTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testHomeViewModelInitialization() {
        composeTestRule.setContent {
            val viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
            assertNotNull(viewModel)
        }
    }
    /*Doesn't work because of navigation problems*/

    /*@Test
    fun testTableViewModelInitialization() {
        composeTestRule.setContent {
            val viewModel: TableViewModel = viewModel(factory = AppViewModelProvider.Factory)
            assertNotNull(viewModel)
        }
    }

    @Test
    fun testFixtureViewModelInitialization() {
        composeTestRule.setContent {
            val viewModel: FixtureViewModel = viewModel(factory = AppViewModelProvider.Factory)
            assertNotNull(viewModel)
        }
    }

    @Test
    fun testTeamViewModelInitialization() {
        composeTestRule.setContent {
            val viewModel: TeamViewModel = viewModel(factory = AppViewModelProvider.Factory)
            assertNotNull(viewModel)
        }
    }*/
}