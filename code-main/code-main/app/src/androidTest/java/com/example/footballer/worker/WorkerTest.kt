package com.example.footballer.worker

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import com.example.footballer.data.AppContainer
import com.example.footballer.data.AppDataContainer
import com.example.footballer.data.FixtureDAO
import com.example.footballer.data.FootballRepository
import com.example.footballer.data.FootballerDatabase
import com.example.footballer.data.OfflineFixtureRepository
import com.example.footballer.data.OfflineRepository
import com.example.footballer.workers.FetchDataWorker
import com.example.footballer.workers.factory.WorkerFactory
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkerTest {
    private lateinit var context: Context
    private lateinit var container : AppContainer

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        container = AppDataContainer(context)
    }

    @Test
    @RequiresApi(Build.VERSION_CODES.O)
    fun testSuccessFetchDataWorker_returnsSuccess() {
        val offlineFootballerRepository = container.offlineFootballerRepository
        val footballerRepository = container.footballerRepository

        val workerFactory = WorkerFactory(offlineFootballerRepository, footballerRepository)

        val worker = TestListenableWorkerBuilder<FetchDataWorker>(context)
            .setWorkerFactory(workerFactory)
            .build()
        runBlocking {
            val result = worker.doWork()
            Assert.assertTrue(result is ListenableWorker.Result.Success)
        }
    }
}