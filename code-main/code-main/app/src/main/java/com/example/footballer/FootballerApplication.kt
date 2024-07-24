package com.example.footballer

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.footballer.data.AppContainer
import com.example.footballer.data.AppDataContainer
import com.example.footballer.data.FootballRepository
import com.example.footballer.data.OfflineFixtureRepository
import com.example.footballer.workers.FetchDataWorker
import com.example.footballer.workers.factory.WorkerFactory
import java.util.concurrent.TimeUnit

class FootballerApplication : Application(), Configuration.Provider{
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

        //initialization of a work manager with periodic task
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val fetchDataRequest = PeriodicWorkRequestBuilder<FetchDataWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "FetchWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            fetchDataRequest
        )
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(WorkerFactory(container.offlineFootballerRepository, container.footballerRepository))
            .build()
}