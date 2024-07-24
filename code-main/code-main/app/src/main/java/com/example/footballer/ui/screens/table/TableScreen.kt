package com.example.footballer.ui.screens.table

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.footballer.NavigationDestination
import com.example.footballer.R
import com.example.footballer.model.LeagueTableRow
import com.example.footballer.model.Team
import com.example.footballer.ui.AppViewModelProvider
import com.example.footballer.ui.theme.FootballerTheme

object TableDestination : NavigationDestination {
    override val route = "league"
    override val titleRes = R.string.table
    const val leagueIdArg = "leagueId"
    val routeWithArgs = "$route/{$leagueIdArg}"
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TableScreen(
    onShareTable: (String, String) -> Unit,
    viewModel: TableViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val tableUiState by viewModel.tableUiState.collectAsState()

    val currentTable = stringResource(R.string.current_table)
    val tableString = tableToString(table = viewModel.tableUiState.value.table)
    val shareTable = stringResource(
        R.string.current_table_standings,
        tableString
    )

    if (tableUiState.table.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.no_data_found),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            IconButton(
                onClick = {
                    onShareTable(currentTable, shareTable)
                },
                modifier = modifier
                    .padding(top = 40.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(R.string.share)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = stringResource(R.string.position), modifier = Modifier.weight(1f))
                Text(text = stringResource(R.string.name_of_team), modifier = Modifier.weight(2f))
                Text(text = stringResource(R.string.goal_difference), modifier = Modifier.weight(1f))
                Text(text = stringResource(R.string.points), modifier = Modifier.weight(1f))
            }

            ScrollableTable(rows = tableUiState.table)
        }
    }
}

@Composable
fun LeagueTableItem(
    item : LeagueTableRow,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .padding(8.dp)
    ) {
        Text(text = item.position.toString(), modifier = Modifier.weight(1f))
        Text(text = item.club.name, modifier = Modifier.weight(2f))
        Text(text = item.goals.toString(), modifier = Modifier.weight(1f))
        Text(text = item.points.toString(), modifier = Modifier.weight(1f))
    }
}

@Composable
fun ScrollableTable(
    rows: List<LeagueTableRow>,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
            .padding(16.dp),
        userScrollEnabled = true,
    ){
        items(rows) { row ->
            LeagueTableItem(item = row)
        }
    }
}

private fun tableToString(
    table: List<LeagueTableRow>
) : String {
    val tableStringBuilder = StringBuilder()

    table.forEach { row ->
        with(row) {
            val position = "Position: $position"
            val clubName = "Club Name: ${club.name}"
            val goals = "Goals: $goals"
            val points = "Points: $points"

            val formattedRow = listOf(position, clubName, goals, points)
                .joinToString(separator = "\n")

            tableStringBuilder.append(formattedRow)
            tableStringBuilder.append("\n\n") // Add newline between rows
        }
    }

    return tableStringBuilder.toString()
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun LeagueReview(){
    FootballerTheme {
        ScrollableTable(
            rows = listOf<LeagueTableRow>(
                LeagueTableRow(
                    position = 1,
                    club =  Team(id = 1, name = "Dortmund", crest = ""),
                    goals = 15,
                    points = 12
                    ),
                LeagueTableRow(
                    position = 2,
                    club =  Team(id = 2, name = "bayern", crest = ""),
                    goals = 12,
                    points = 9
                )
            )
        )
    }
}