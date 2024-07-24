package com.example.footballer.ui.screens.home

import com.example.footballer.model.Fixture
import com.example.footballer.model.FullTime
import com.example.footballer.model.HalfTime
import com.example.footballer.model.League
import com.example.footballer.model.Score
import com.example.footballer.model.Team
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    val leagues: List<League> = listOf(),
    val fixtures: List<Fixture> = listOf(),
)