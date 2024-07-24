package com.example.footballer.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import com.example.footballer.R
import com.example.footballer.model.League
import com.example.footballer.model.LeagueTableRow
import com.example.footballer.ui.screens.home.HomeDestination
import com.example.footballer.ui.screens.table.TableDestination

object DataSource {
    val leagues = listOf(
        "Premier League",
        "Primera Division",
        "Bundesliga",
        "Eredivisie",
        "Serie A",
    )

    val leaguesShort = listOf(
        "PL",
        "BL1",
        "SA",
        "PD",
        "DED"
    )

    val navigationItems = listOf(
        NavItems(
            title = R.string.app_name,
            selected = Icons.Filled.Home,
            unselected = Icons.Rounded.Home,
            route = HomeDestination.route
        ),
        NavItems(
            title = R.string.league,
            selected = Icons.Filled.List,
            unselected = Icons.Rounded.List,
            route = TableDestination.route
        )
    )
}