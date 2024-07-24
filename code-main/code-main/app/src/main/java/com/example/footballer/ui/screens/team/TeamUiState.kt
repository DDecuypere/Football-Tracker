package com.example.footballer.ui.screens.team

import com.example.footballer.model.Player
import com.example.footballer.model.TeamInfo

data class TeamUiState(
    val team : TeamInfo = emptyTeamInfo()
)

private fun emptyTeamInfo() : TeamInfo {
    return TeamInfo(
        id = 0,
        name = "",
        crest = "",
        venue = "",
        founded = "",
        squad = listOf(Player(id = 0, name = "", position = ""))
    )
}
