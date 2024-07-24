package com.example.footballer.ui.screens.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.footballer.data.FootballRepository
import com.example.footballer.data.NetworkFootballRepository
import com.example.footballer.data.OfflineFixtureRepository
import com.example.footballer.data.OfflineRepository
import com.example.footballer.model.Fixture
import com.example.footballer.model.League
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.Console
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.log

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(
    private val footballRepository: FootballRepository,
    private val offlineRepo: OfflineFixtureRepository
): ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState : StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    init {
        fetchData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchData() {
        viewModelScope.launch {
            try {
                // Fetch matches and leagues from the repository
                val leagues = footballRepository.getLeagues()
                offlineRepo.getOfflineFixtures().collect {fixtureList ->
                    _homeUiState.update { currentState ->
                        currentState.copy(
                            leagues = leagues,
                            fixtures = fixtureList
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching data: ${e.message}", e)
                //on exception set ui state to offline database data
                offlineRepo.getOfflineFixtures().collect {fixtureList ->
                    _homeUiState.update { currentState ->
                        currentState.copy(
                            fixtures = fixtureList
                        )
                    }
                }
            }
        }
    }
}