package com.example.footballer.ui.screens.team

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.footballer.NavigationDestination
import com.example.footballer.R
import com.example.footballer.model.Player
import com.example.footballer.model.Team
import com.example.footballer.model.TeamInfo
import com.example.footballer.ui.AppViewModelProvider
import com.example.footballer.ui.theme.FootballerTheme

object TeamDestination : NavigationDestination {
    override val route = "team"
    override val titleRes = R.string.team
    const val teamIdArg = "teamId"
    val routeWithArgs = "$route/{$teamIdArg}"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TeamScreen(
    viewModel: TeamViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    val teamUiState by viewModel.teamUiState.collectAsState()

    if (teamUiState.team.id == 0) {
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
                .padding(top = 48.dp)
        ) {
            TeamRepresentation(
                team = teamUiState.team
            )
            TeamInfoBox(
                team = teamUiState.team
            )
            TeamSquad(
                squad = teamUiState.team.squad
            )
        }
    }
}

@Composable
fun TeamRepresentation(
    team: TeamInfo,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(team.crest)
                .crossfade(true).build(),
            contentDescription = stringResource(R.string.crest),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(70.dp)
        )
        Text(
            text = team.name,
            modifier = modifier
                .padding(8.dp)
        )
    }
}

@Composable
fun TeamInfoBox(
    team : TeamInfo,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(48.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.venue),
                fontWeight = FontWeight.Bold,
                modifier = modifier.weight(1f)
            )
            Text(
                text = team.venue,
                modifier = modifier.weight(1f)
            )
        }
        Row(
            modifier = modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.founded),
                fontWeight = FontWeight.Bold,
                modifier = modifier.weight(1f)
            )
            Text(
                text = team.founded,
                modifier = modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TeamSquad(
    squad: List<Player>,
    modifier: Modifier = Modifier
        .padding(16.dp)
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.squad),
            fontWeight = FontWeight.ExtraBold,
            modifier = modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium,
        )
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
        ){
            items(squad) {player ->
                PlayerInfo(player = player)
            }
        }
    }
}

@Composable
fun PlayerInfo(
    player: Player,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = player.name,
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
        Text(
            text = player.position,
            modifier = modifier
                .padding(end = 8.dp)
        )
    }
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
fun TeamPreview() {
    FootballerTheme {
        TeamRepresentation(
            team = TeamInfo(
                id = 1,
                name = "united",
                crest = "",
                founded = "1900",
                venue = "Wembley",
                squad = listOf<Player>(
                    Player(
                        id = 1,
                        name = "harold",
                        position = "keeper"
                    ),
                    Player(
                        id = 2,
                        name = "tim",
                        position = "defender"
                    )
                )
            )
        )
        TeamInfoBox(
            team = TeamInfo(
                id = 1,
                name = "united",
                crest = "",
                founded = "1900",
                venue = "Wembley",
                squad = listOf<Player>(
                    Player(
                        id = 1,
                        name = "harold",
                        position = "keeper"
                    )
                )
            )
        )
        TeamSquad(
            squad = listOf<Player>(
                Player(
                    id = 1,
                    name = "harold",
                    position = "keeper"
                ),
                Player(
                    id = 2,
                    name = "tim",
                    position = "defender"
                )
            )
        )
    }
}