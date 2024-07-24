package com.example.footballer.workers.factory

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.footballer.data.FootballRepository
import com.example.footballer.data.OfflineFixtureRepository
import com.example.footballer.workers.FetchDataWorker

class WorkerFactory(
    private val offlineRepo: OfflineFixtureRepository,
    private val onlineRepo: FootballRepository
) : WorkerFactory() {

    override fun createWorker(
        ctx: Context,
        workerClassName: String,
        params: WorkerParameters
    ): ListenableWorker = FetchDataWorker(ctx, params, offlineRepo, onlineRepo)
}