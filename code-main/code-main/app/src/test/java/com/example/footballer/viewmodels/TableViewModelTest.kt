package com.example.footballer.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.example.footballer.fake.FakeDataSource
import com.example.footballer.fake.FakeNetworkRepository
import com.example.footballer.rules.TestDispatcherRule
import com.example.footballer.ui.screens.table.TableDestination
import com.example.footballer.ui.screens.table.TableUiState
import com.example.footballer.ui.screens.table.TableViewModel
import com.example.footballer.ui.screens.team.TeamDestination
import com.example.footballer.ui.screens.team.TeamUiState
import com.example.footballer.ui.screens.team.TeamViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TableViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var tableViewModel: TableViewModel
    @Before
    fun setup() {
        val savedStateHandle = SavedStateHandle()

        savedStateHandle.set(TableDestination.leagueIdArg, 1)

        tableViewModel = TableViewModel(savedStateHandle, FakeNetworkRepository())
    }
    @Test
    fun `get a table and check if ui state is correct` () =
        runTest {
            Assert.assertEquals(
                TableUiState(FakeDataSource.leagueTable),
                tableViewModel.tableUiState.first()
            )
        }
}