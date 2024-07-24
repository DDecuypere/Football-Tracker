package com.example.footballer.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.footballer.model.League
import com.example.footballer.model.LeagueTableRow

class NavViewModel: ViewModel() {
    var selectedItemIndex = mutableStateOf(0)

    fun setSelectedItemIndex(index: Int) {
        selectedItemIndex.value = index
    }
}