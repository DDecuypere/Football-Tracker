package com.example.footballer.fake

import com.example.footballer.model.Fixture
import com.example.footballer.model.FullTime
import com.example.footballer.model.HalfTime
import com.example.footballer.model.League
import com.example.footballer.model.LeagueTableRow
import com.example.footballer.model.Player
import com.example.footballer.model.Score
import com.example.footballer.model.Team
import com.example.footballer.model.TeamInfo
import java.util.concurrent.Flow

object FakeDataSource {
    /***
    API DATA
    ***/
    val leagues = listOf<League>(
        League(id = 1, name = "Bundesliga")
    )
    val fixtures = listOf<Fixture>(
        Fixture(
            id = 1,
            comp = League(id = 1, name = "Bundesliga"),
            homeTeam = Team(id = 1, name = "dortmund", crest = ""),
            awayTeam = Team(id = 2, name = "bayern", crest = ""),
            status = "finished",
            dateUtc = "",
            score = Score(
                winner = null,
                duration = "",
                halfTime = HalfTime(1, 2),
                fullTime = FullTime(1, 1)
            )
        ),
        Fixture(
            id = 2,
            comp = League(id = 1, name = "Bundesliga"),
            homeTeam = Team(id = 2, name = "bayer", crest = ""),
            awayTeam = Team(id = 1, name = "dortmund", crest = ""),
            status = "timed",
            dateUtc = "2024-03-02",
            score = Score(
                winner = null,
                duration = "",
                halfTime = HalfTime(null, null),
                fullTime = FullTime(null, null)
            )
        )
    )
    val leagueTable = listOf<LeagueTableRow>(
        LeagueTableRow(position = 1, club = Team(id = 1, name = "dortmund", crest = ""), goals = 15, points = 20),
        LeagueTableRow(position = 2, club = Team(id = 2, name = "bayern", crest = ""), goals = 18, points = 17)
    )
    val teamInfo = TeamInfo(
        id = 1,
        name = "dortmund",
        crest = "",
        squad = listOf<Player>(
            Player(id = 1, name = "henk", position = "defender"),
            Player(id = 2, name = "jan", position = "goalkeeper")
        ),
        founded = "1900",
        venue = "park"
    )

    /***
    DB DATA
     ***/
    var currentFixtures = mutableListOf(
        Fixture(
            id = 1,
            comp = League(id = 1, name = "Bundesliga"),
            homeTeam = Team(id = 1, name = "dortmund", crest = ""),
            awayTeam = Team(id = 2, name = "bayern", crest = ""),
            status = "finished",
            dateUtc = "2024-03-07",
            score = Score(
                winner = null,
                duration = "",
                halfTime = HalfTime(1, 2),
                fullTime = FullTime(1, 1)
            )
        ),
        Fixture(
            id = 2,
            comp = League(id = 1, name = "Bundesliga"),
            homeTeam = Team(id = 2, name = "bayer", crest = ""),
            awayTeam = Team(id = 1, name = "dortmund", crest = ""),
            status = "timed",
            dateUtc = "2024-03-02",
            score = Score(
                winner = null,
                duration = "",
                halfTime = HalfTime(null, null),
                fullTime = FullTime(null, null)
            )
        )
    )
}