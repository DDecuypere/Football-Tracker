package com.example.footballer.data

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItems(
    val title: Int,
    val selected: ImageVector,
    val unselected: ImageVector,
    val route: String
)
