package com.example.footballer.workers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.footballer.data.FootballRepository
import com.example.footballer.data.OfflineFixtureRepository
import com.example.footballer.model.Fixture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private const val TAG = "FetchWorker"
class FetchDataWorker(
    ctx: Context,
    params: WorkerParameters,
    private val offlineRepo : OfflineFixtureRepository,
    private val onlineRepo: FootballRepository
): CoroutineWorker(ctx, params) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result = withContext(Dispatchers.Default) {
        try {
            val fixtures = onlineRepo.getFixtures()
            updateDatabase(fixtures)
            Log.d(TAG, "New Data from worker")
            Result.success()
        }catch (e: Exception) {
            Log.e(TAG, "Error in Worker", e)
            Result.failure()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun updateDatabase(
        fixtures: List<Fixture>,
    ){
        val formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val currentDateTimeUtc = LocalDateTime.now().atOffset(ZoneOffset.UTC)
        val date = formatterDate.format(currentDateTimeUtc.minusDays(1))
        try {
            offlineRepo.deleteOldFixture(date)
            for (fixture in fixtures){
                val existingFixture = withContext(Dispatchers.IO) {
                    offlineRepo.getOfflineFixture(fixture.id).firstOrNull()
                }
                if (existingFixture != null) {
                    if (
                        existingFixture.status != fixture.status ||
                        existingFixture.score.fullTime.home != fixture.score.fullTime.home ||
                        existingFixture.score.fullTime.away != fixture.score.fullTime.away ||
                        existingFixture.score.halfTime.home != fixture.score.halfTime.home ||
                        existingFixture.score.halfTime.away != fixture.score.halfTime.away
                        ) {
                            offlineRepo.updateFixture(fixture)
                        }
                }else {
                    if (validateInput(fixture)) {
                        offlineRepo.insertFixture(fixture)
                    }
                }
            }
        }catch (e:Exception){
            Log.e(TAG, "Error with inserting data into database", e)
        }
    }

    private fun validateInput(
        fixture: Fixture,
    ): Boolean {
        return fixture.id > 0 && fixture.comp.name.isNotBlank() && fixture.comp.id > 0 &&
                fixture.homeTeam.name.isNotBlank() && fixture.homeTeam.crest.isNotBlank() &&
                fixture.awayTeam.name.isNotBlank() && fixture.awayTeam.crest.isNotBlank() &&
                fixture.status.isNotBlank() && fixture.score.duration.isNotBlank() &&
                fixture.dateUtc.isNotBlank()
    }
}