package com.example.footballer.ui.screens.fixture

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballer.data.OfflineFixtureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FixtureViewModel(
    savedStateHandle: SavedStateHandle,
    private val offlineRepo: OfflineFixtureRepository
): ViewModel() {
    private val _fixtureUiState = MutableStateFlow(FixtureUiState())
    val fixtureUiState : StateFlow<FixtureUiState> = _fixtureUiState.asStateFlow()

    private val fixtureId: Int = checkNotNull(savedStateHandle[FixtureDestination.fixtureIdArg])

    init {
        fetchFixture()
    }

    private fun fetchFixture() {
        viewModelScope.launch {
            try {
                offlineRepo.getOfflineFixture(fixtureId)
                    .collect { fixture ->
                        _fixtureUiState.update { currentState ->
                            currentState.copy(
                                fixture = fixture
                            )
                        }
                    }
            } catch (e: Exception) {
                Log.e("FixtureViewModel", "Error fetching fixture: ${e.message}", e)
            }
        }
    }
}