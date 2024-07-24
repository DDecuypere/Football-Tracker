package com.example.footballer.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class FixtureConverter {
    @TypeConverter
    fun fromScoreToString(score: Score?): String? {
        return Gson().toJson(score)
    }

    @TypeConverter
    fun toScoreFromString(scoreStr: String?): Score? {
        return Gson().fromJson(scoreStr, Score::class.java)
    }
    @TypeConverter
    fun fromFullTimeToString(fullTime: FullTime?): String? {
        return fullTime?.let {
            "${it.home},${it.away}"
        }
    }

    @TypeConverter
    fun toFullTimeFromString(fullTimeStr: String?): FullTime? {
        return fullTimeStr?.split(",")?.let {
            FullTime(it[0].toInt(), it[1].toInt())
        }
    }

    @TypeConverter
    fun fromHalfTimeToString(halfTime: HalfTime?): String? {
        return halfTime?.let {
            "${it.home},${it.away}"
        }
    }

    @TypeConverter
    fun toHalfTimeFromString(halfTimeStr: String?): HalfTime? {
        return halfTimeStr?.split(",")?.let {
            HalfTime(it[0].toInt(), it[1].toInt())
        }
    }

    @TypeConverter
    fun toLeagueFromString(value: String?): League? {
        return Gson().fromJson(value, League::class.java)
    }

    @TypeConverter
    fun fromLeagueToString(league: League?): String? {
        return Gson().toJson(league)
    }

    @TypeConverter
    fun toTeamFromString(value: String?): Team? {
        return Gson().fromJson(value, Team::class.java)
    }

    @TypeConverter
    fun fromTeamToString(team: Team?): String? {
        return Gson().toJson(team)
    }
}
