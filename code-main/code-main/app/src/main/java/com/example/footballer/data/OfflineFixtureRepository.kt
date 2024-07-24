package com.example.footballer.data

import android.util.Log
import com.example.footballer.model.Fixture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

interface OfflineFixtureRepository {
    fun getOfflineFixtures(): Flow<List<Fixture>>
    fun getOfflineFixture(id: Int): Flow<Fixture>
    suspend fun insertFixture(fixture: Fixture)
    suspend fun deleteOldFixture(date: String)
    suspend fun updateFixture(fixture: Fixture)

}
class OfflineRepository(private val fixtureDAO: FixtureDAO) : OfflineFixtureRepository {
    //create a flow that emits data from DAO via IO thread (avoid UI thread)
    override fun getOfflineFixtures(): Flow<List<Fixture>> = flow {
        emitAll(fixtureDAO.getCurrentFixtures())
    }.flowOn(Dispatchers.IO)

    override fun getOfflineFixture(id: Int): Flow<Fixture> = fixtureDAO.getFixture(id)
    override suspend fun insertFixture(fixture: Fixture) = fixtureDAO.insert(fixture)

    override suspend fun deleteOldFixture(date: String) = fixtureDAO.delete(date)

    override suspend fun updateFixture(fixture: Fixture) = fixtureDAO.update(fixture)
}