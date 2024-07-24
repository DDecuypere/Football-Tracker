package com.example.footballer.ui

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.footballer.FootballerApplication
import com.example.footballer.ui.screens.fixture.FixtureViewModel
import com.example.footballer.ui.screens.home.HomeViewModel
import com.example.footballer.ui.screens.table.TableViewModel
import com.example.footballer.ui.screens.team.TeamViewModel

object AppViewModelProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(footballerApplication().container.footballerRepository, footballerApplication().container.offlineFootballerRepository)
        }
        // Initializer for TableViewModel
        initializer {
            TableViewModel(
                this.createSavedStateHandle(),
                footballerApplication().container.footballerRepository
            )
        }
        // Initializer for FixtureViewModel
        initializer {
            FixtureViewModel(
                this.createSavedStateHandle(),
                footballerApplication().container.offlineFootballerRepository
            )
        }
        // Initializer for TeamViewModel
        initializer {
            TeamViewModel(
                this.createSavedStateHandle(),
                footballerApplication().container.footballerRepository
            )
        }
    }
}
fun CreationExtras.footballerApplication() : FootballerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FootballerApplication)