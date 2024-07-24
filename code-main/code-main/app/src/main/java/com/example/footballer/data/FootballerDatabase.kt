package com.example.footballer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.footballer.model.Fixture
import com.example.footballer.model.FixtureConverter

@Database(entities = [Fixture::class], version = 2, exportSchema = false)
@TypeConverters(FixtureConverter::class)
abstract class FootballerDatabase : RoomDatabase() {
    abstract fun fixtureDao(): FixtureDAO

    companion object {
        @Volatile
        private var Instance: FootballerDatabase? = null

        fun getDatabase(context: Context): FootballerDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FootballerDatabase::class.java, "fixture_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}