package com.example.footballer.fake

import com.example.footballer.data.FixtureDAO
import com.example.footballer.model.Fixture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFixtureDao: FixtureDAO {
    override fun getCurrentFixtures(): Flow<List<Fixture>> {
        return flowOf(FakeDataSource.currentFixtures)
    }

    override fun getFixture(id: Int): Flow<Fixture> {
        val fixture = FakeDataSource.currentFixtures.find { it.id == id }
        return if (fixture != null) {
            flowOf(fixture)
        } else {
            flowOf()
        }
    }

    override suspend fun insert(fixture: Fixture) {
        FakeDataSource.currentFixtures.add(fixture)
    }

    override suspend fun delete(date: String) {
        FakeDataSource.currentFixtures.removeIf{ it.dateUtc < date }
    }

    override suspend fun update(fixture: Fixture) {
        val updatedFixtures = FakeDataSource.currentFixtures.map {
            if (it.id == fixture.id) fixture else it }.toMutableList()
        FakeDataSource.currentFixtures.clear()
        FakeDataSource.currentFixtures.addAll(updatedFixtures)
    }
}