package com.example.footballer.ui.screens.fixture

import android.content.res.Configuration
import android.nfc.Tag
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.example.footballer.NavigationDestination
import com.example.footballer.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.footballer.model.Fixture
import com.example.footballer.model.FullTime
import com.example.footballer.model.HalfTime
import com.example.footballer.model.League
import com.example.footballer.model.Score
import com.example.footballer.model.Team
import com.example.footballer.ui.AppViewModelProvider
import com.example.footballer.ui.theme.FootballerTheme

object FixtureDestination : NavigationDestination {
    override val route = "fixture"
    override val titleRes = R.string.fixture
    const val fixtureIdArg = "fixtureId"
    val routeWithArgs = "$route/{$fixtureIdArg}"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FixtureScreen(
    modifier: Modifier = Modifier,
    viewModel: FixtureViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onTeamClick: (Int) -> Unit
) {
    val fixtureUiState by viewModel.fixtureUiState.collectAsState()
    FixtureOverview(
        fixture = fixtureUiState.fixture,
        onTeamClick = onTeamClick
    )
}

@Composable
fun FixtureOverview(
    fixture: Fixture,
    onTeamClick: (Int) -> Unit,
    modifier: Modifier = Modifier
        .fillMaxWidth()
) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text(
                text = fixture.comp.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 48.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamInfo(team = fixture.homeTeam, onTeamClick = onTeamClick)
                Text(
                    text = fixture.status.toLowerCase(),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )
                TeamInfo(team = fixture.awayTeam, onTeamClick = onTeamClick)
            }
            scoreOverview(score = fixture.score)
        }
}

@Composable
fun TeamInfo(
    team: Team,
    onTeamClick: (Int) -> Unit,
    testTag: String = "TeamCrest",
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .widthIn(max = 100.dp)
            .padding(vertical = 16.dp),
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
                .clickable {
                    onTeamClick(team.id)
                }
                .testTag(testTag),
            alignment = Alignment.Center
        )
        Text(
            text = team.name,
            modifier = modifier
                .padding(8.dp),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun scoreOverview(
    score: Score,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        halfTimeOverview(score.halfTime)
        fullTimeOverview(score.fullTime)
    }
}



@Composable
fun halfTimeOverview(
    halfTime: HalfTime,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.halfTime),
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = halfTime.home?.toString() ?: "0",
                modifier = modifier
            )
            Text(
                text = halfTime.away?.toString() ?: "0",
                modifier = modifier
            )
        }
    }
}

@Composable
fun fullTimeOverview(
    fullTime: FullTime,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.fullTime),
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = fullTime.home?.toString() ?: "0",
                modifier = modifier
            )
            Text(
                text = fullTime.away?.toString() ?: "0",
                modifier = modifier
            )
        }
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
fun FixturePreview() {
    FootballerTheme {
        FixtureOverview(
            onTeamClick = {},
            fixture = Fixture(
                id = 1,
                comp = League(id = 1, name = "bundesliga"),
                homeTeam = Team(id = 1, name = "Dortmund", crest = ""),
                awayTeam = Team(id = 2, name = "Bayern", crest = ""),
                status = "finished",
                dateUtc = "",
                score = Score(
                    winner = null,
                    duration = "",
                    halfTime = HalfTime(1, 1),
                    fullTime = FullTime(1, 1)
                )
            )
        )
    }
}