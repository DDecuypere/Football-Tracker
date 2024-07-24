package com.example.footballer.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.example.footballer.fake.FakeDataSource
import com.example.footballer.fake.FakeNetworkRepository
import com.example.footballer.fake.FakeOfflineRepository
import com.example.footballer.rules.TestDispatcherRule
import com.example.footballer.ui.screens.home.HomeUiState
import com.example.footballer.ui.screens.home.HomeViewModel
import com.example.footballer.ui.screens.team.TeamDestination
import com.example.footballer.ui.screens.team.TeamUiState
import com.example.footballer.ui.screens.team.TeamViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TeamViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var teamViewModel: TeamViewModel
    @Before
    fun setup() {
        val savedStateHandle = SavedStateHandle()

        savedStateHandle.set(TeamDestination.teamIdArg, 1)

        teamViewModel = TeamViewModel(savedStateHandle, FakeNetworkRepository())
    }
    @Test
    fun `get a team and check if ui state is correct` () =
        runTest {
            Assert.assertEquals(
                TeamUiState(FakeDataSource.teamInfo),
                teamViewModel.teamUiState.first()
            )
        }
}