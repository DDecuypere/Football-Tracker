package com.example.footballer.ui.screens.team

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballer.data.FootballRepository
import com.example.footballer.ui.screens.fixture.FixtureDestination
import com.example.footballer.ui.screens.fixture.FixtureUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeamViewModel(
    savedStateHandle: SavedStateHandle,
    private val onlineRepo: FootballRepository
): ViewModel() {
    private val _teamUiState = MutableStateFlow(TeamUiState())
    val teamUiState : StateFlow<TeamUiState> = _teamUiState.asStateFlow()

    private val teamId: Int = checkNotNull(savedStateHandle[TeamDestination.teamIdArg])

    init {
        fetchTeam()
    }

    private fun fetchTeam() {
        viewModelScope.launch {
            try {
                val team = onlineRepo.getTeamInfo(teamId)

                _teamUiState.update { currentState ->
                    currentState.copy(
                        team = team
                    )
                }
            } catch (e: Exception) {
                Log.e("TeamViewModel", "Error fetching team: ${e.message}", e)
            }
        }
    }
}