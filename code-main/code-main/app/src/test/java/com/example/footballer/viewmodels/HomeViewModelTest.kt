package com.example.footballer.viewmodels

import com.example.footballer.data.FootballRepository
import com.example.footballer.data.OfflineFixtureRepository
import com.example.footballer.fake.FakeDataSource
import com.example.footballer.fake.FakeNetworkRepository
import com.example.footballer.fake.FakeOfflineRepository
import com.example.footballer.rules.TestDispatcherRule
import com.example.footballer.ui.screens.home.HomeUiState
import com.example.footballer.ui.screens.home.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()
    @Test
    fun `get leagues and check if ui state is correct` () =
        runTest {
            val homeViewModel = HomeViewModel(
                footballRepository = FakeNetworkRepository(),
                offlineRepo = FakeOfflineRepository()
            )
            assertEquals(
                HomeUiState(leagues = FakeDataSource.leagues).leagues,
                homeViewModel.homeUiState.value.leagues
            )
        }

    @Test
    fun `get fixtures and check if ui state is correct` () =
        runTest {
            val homeViewModel = HomeViewModel(
                footballRepository = FakeNetworkRepository(),
                offlineRepo = FakeOfflineRepository()
            )
            assertEquals(
                HomeUiState(fixtures = FakeDataSource.currentFixtures).fixtures,
                homeViewModel.homeUiState.value.fixtures
            )
        }

    @Test
    fun `get fixtures and leagues, check if ui state is correct` () =
        runTest {
            val homeViewModel = HomeViewModel(
                footballRepository = FakeNetworkRepository(),
                offlineRepo = FakeOfflineRepository()
            )
            assertEquals(
                HomeUiState(fixtures = FakeDataSource.currentFixtures, leagues = FakeDataSource.leagues),
                homeViewModel.homeUiState.first()
            )
        }

    /*@Test()
    fun homeViewModel_getFixturesOnException_verifyHomeUiState() =
        runTest {
            val homeViewModel = HomeViewModel(
                footballRepository = FakeNetworkRepository(),
                offlineRepo = FakeOfflineRepository()
            )
            assertEquals(
                HomeUiState(fixtures = FakeDataSource.currentFixtures),
                homeViewModel.homeUiState.first()
            )

        }*/
}