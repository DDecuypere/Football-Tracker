package com.example.footballer.ui.screens.table

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballer.data.FootballRepository
import com.example.footballer.model.LeagueTableRow
import com.example.footballer.model.Table
import com.example.footballer.ui.screens.home.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.log

class TableViewModel(
    savedStateHandle: SavedStateHandle,
    private val footballRepository: FootballRepository,
): ViewModel() {
    private val _tableUiState = MutableStateFlow(TableUiState())
    val tableUiState : StateFlow<TableUiState> = _tableUiState.asStateFlow()

    private val leagueId: Int = checkNotNull(savedStateHandle[TableDestination.leagueIdArg])

    init {
        fetchTable()
    }

    private fun fetchTable() {
        viewModelScope.launch {
            try {
                // Fetch table of league
                val response = footballRepository.getLeagueTable(leagueId)

                // Update the UI state with new table
                _tableUiState.update { currentState ->
                    currentState.copy(
                        table = response.table
                    )
                }
            } catch (e: Exception) {
                Log.e("TableViewModel", "Error fetching table: ${e.message}", e)
            }
        }
    }
}
