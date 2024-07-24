package com.example.footballer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.footballer.model.Fixture
import kotlinx.coroutines.flow.Flow

@Dao
interface FixtureDAO {
    @Query("SELECT * FROM fixtures")
    fun getCurrentFixtures(): Flow<List<Fixture>>

    @Query("SELECT * FROM fixtures WHERE id = :id")
    fun getFixture(id: Int): Flow<Fixture>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(fixture: Fixture)

    @Query("DELETE FROM fixtures WHERE dateUtc < :date")
    suspend fun delete(date: String)

    @Update
    suspend fun update(fixture: Fixture)
}
