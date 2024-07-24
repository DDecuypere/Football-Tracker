package com.example.footballer.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.example.footballer.fake.FakeDataSource
import com.example.footballer.fake.FakeNetworkRepository
import com.example.footballer.fake.FakeOfflineRepository
import com.example.footballer.rules.TestDispatcherRule
import com.example.footballer.ui.screens.fixture.FixtureDestination
import com.example.footballer.ui.screens.fixture.FixtureUiState
import com.example.footballer.ui.screens.fixture.FixtureViewModel
import com.example.footballer.ui.screens.table.TableDestination
import com.example.footballer.ui.screens.table.TableUiState
import com.example.footballer.ui.screens.table.TableViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FixtureViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var fixtureViewModel: FixtureViewModel
    @Before
    fun setup() {
        val savedStateHandle = SavedStateHandle()

        savedStateHandle.set(FixtureDestination.fixtureIdArg, 1)

        fixtureViewModel = FixtureViewModel(savedStateHandle, FakeOfflineRepository())
    }
    @Test
    fun `get a fixture and check if ui state is correct` () =
        runTest {
            Assert.assertEquals(
                FixtureUiState(FakeDataSource.currentFixtures[0]),
                fixtureViewModel.fixtureUiState.first()
            )
        }
}