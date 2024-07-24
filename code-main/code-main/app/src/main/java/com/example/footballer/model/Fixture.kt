package com.example.footballer.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "fixtures")
data class Fixture(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @TypeConverters
    @SerializedName("competition")
    val comp: League,
    @TypeConverters
    @SerializedName("homeTeam")
    val homeTeam: Team,
    @TypeConverters
    @SerializedName("awayTeam")
    val awayTeam: Team,
    @SerializedName("status")
    val status: String,
    @TypeConverters
    @SerializedName("score")
    val score: Score,
    @SerializedName("utcDate")
    val dateUtc: String
)

data class FixturesResponse(
    @SerializedName("matches")
    val matches: List<Fixture>
)

data class Score(
    @SerializedName("winner")
    val winner: String?,
    @SerializedName("duration")
    val duration: String,
    @TypeConverters
    @SerializedName("fullTime")
    val fullTime: FullTime,
    @TypeConverters
    @SerializedName("halfTime")
    val halfTime: HalfTime
)

data class FullTime(
    @SerializedName("home")
    val home: Int?,
    @SerializedName("away")
    val away: Int?
)

data class HalfTime(
    @SerializedName("home")
    val home: Int?,
    @SerializedName("away")
    val away: Int?
)