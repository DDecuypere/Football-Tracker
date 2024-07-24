package com.example.footballer.ui.screens.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build

import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.footballer.NavigationDestination
import com.example.footballer.R
import com.example.footballer.model.Fixture
import com.example.footballer.model.FullTime
import com.example.footballer.model.HalfTime
import com.example.footballer.model.League
import com.example.footballer.model.Score
import com.example.footballer.model.Team
import com.example.footballer.ui.AppViewModelProvider
import com.example.footballer.ui.theme.FootballerTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}
@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen (
    onLeagueClick: (Int) -> Unit,
    onFixtureClick: (Int) -> Unit,
    onShareFixtureClick: (String, String) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val currentFixture = stringResource(R.string.current_fixture)
        Column (
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            LeagueRow(leagues = homeUiState.leagues, onLeagueClick = onLeagueClick)
            LazyColumn(
                modifier = modifier,
                userScrollEnabled = true,
            ){
                items(homeUiState.fixtures) { fixture ->
                    val fixtureOverview = stringResource(
                        R.string.fixture_details,
                        fixture.homeTeam.name,
                        overallScore(fixture.score.halfTime, fixture.score.fullTime),
                        fixture.awayTeam.name
                    )
                    IconButton(
                        onClick = { onShareFixtureClick(currentFixture, fixtureOverview)},
                        modifier = modifier
                            .padding(top = 80.dp)
                    ){
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = stringResource(R.string.share)
                        )
                    }
                    FixtureCard(fixture = fixture, onFixtureClick = onFixtureClick)
                }
            }
        }
}

@Composable
fun LeagueRow (
    onLeagueClick: (Int) -> Unit,
    leagues: List<League>,
    testTag: String = "Table",
    modifier: Modifier = Modifier
){
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        userScrollEnabled = true
    ){
        items(leagues){
            league ->
            Button(
                onClick = {
                    onLeagueClick(league.id)
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .width(70.dp + 24.dp * 2)
                    .height(50.dp)
                    .padding(8.dp)
                    .testTag(testTag)
            )
        {
            Text(
                text = league.name,
                modifier = modifier
                    .fillMaxWidth(),
                style = TextStyle(fontSize = 12.sp),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )
        }
        }
    }
}

@Composable
fun FixtureCard(
    fixture: Fixture,
    testTag: String = "Fixture",
    onFixtureClick: (Int) -> Unit,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onFixtureClick(fixture.id)
        }
        .testTag(testTag)
) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TeamInfo(team = fixture.homeTeam)
            FixtureInfo(score = fixture.score, status = fixture.status)
            TeamInfo(team = fixture.awayTeam)
        }
}

@Composable
fun FixtureInfo(
    score: Score,
    status: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier= modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = status.toLowerCase(),
        )
        Text(
            text = overallScore(score.halfTime, score.fullTime),
            modifier = modifier.padding(8.dp),
        )
    }
}

@Composable
fun TeamInfo(
    team: Team,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(vertical = 16.dp)
            .widthIn(max = 100.dp),
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
                .padding(8.dp),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

fun overallScore(halfTimeScore: HalfTime, fullTimeScore: FullTime): String{
    val halfTimeHome = halfTimeScore.home
    val halfTimeAway = halfTimeScore.away
    val fullTimeHome = fullTimeScore.home
    val fullTimeAway = fullTimeScore.away

    if (halfTimeHome == null && fullTimeHome == null && halfTimeAway == null && fullTimeAway == null) {
        return ""
    }
    val totalHome = checkCurrentScore(halfTimeHome, fullTimeHome)
    val totalAway = checkCurrentScore(halfTimeAway, fullTimeAway)
    return "$totalHome - $totalAway"
}

fun checkCurrentScore(halfTime: Int?, fullTime: Int?): Int{
    return fullTime ?: (halfTime ?: 0)
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
fun HomePreview() {
    FootballerTheme {
        LeagueRow(
            onLeagueClick = {},
            leagues = listOf<League>(
                League(
                    id = 1,
                    name = "bundesliga"
                ),
                League(
                    id = 2,
                    name = "premier league"
                )
            )
        )
        FixtureCard(
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
            ),
            onFixtureClick = {}
        )
    }
}
