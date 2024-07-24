package com.example.footballer.fake

import com.example.footballer.data.OfflineFixtureRepository
import com.example.footballer.model.Fixture
import com.example.footballer.model.FullTime
import com.example.footballer.model.HalfTime
import com.example.footballer.model.League
import com.example.footballer.model.Score
import com.example.footballer.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeOfflineRepository: OfflineFixtureRepository {
    override fun getOfflineFixtures(): Flow<List<Fixture>> {
        return flowOf(FakeDataSource.currentFixtures)
    }

    override fun getOfflineFixture(id: Int): Flow<Fixture> {
        val fixture = FakeDataSource.currentFixtures.find { it.id == id }
        return if (fixture != null) {
            flowOf(fixture)
        } else {
            flowOf()
        }
    }

    override suspend fun insertFixture(fixture: Fixture) {
        FakeDataSource.currentFixtures.add(fixture)
    }

    override suspend fun deleteOldFixture(date: String) {
        FakeDataSource.currentFixtures.removeIf { it.dateUtc < date }
    }

    override suspend fun updateFixture(fixture: Fixture) {
        val updatedFixtures = FakeDataSource.currentFixtures.map { if (it.id == fixture.id) fixture else it }.toMutableList()
        FakeDataSource.currentFixtures.clear()
        FakeDataSource.currentFixtures.addAll(updatedFixtures)
    }
}