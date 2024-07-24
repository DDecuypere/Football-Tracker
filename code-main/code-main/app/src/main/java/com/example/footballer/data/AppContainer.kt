package com.example.footballer.data

import android.content.Context
import com.example.footballer.network.APIService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val footballerRepository: FootballRepository
    val offlineFootballerRepository: OfflineFixtureRepository
}

/**
 * Expose repositories to whole app so they can be used to use api and local room db
 */

class AppDataContainer(context: Context): AppContainer{
    private val baseUrl =
        "https://api.football-data.org/v4/"

    private val apiKey =
        "1a6aa8eeeac649179fc3c86e1e236e95"


    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-Auth-Token", apiKey)
                .build()
            chain.proceed(request)
        }
    }.build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(httpClient)
        .build()

    private val retrofitService: APIService by lazy {
        retrofit.create(APIService::class.java)
    }

    override val footballerRepository: FootballRepository by lazy {
        NetworkFootballRepository(retrofitService)
    }

    override val offlineFootballerRepository: OfflineFixtureRepository by lazy {
        OfflineRepository(FootballerDatabase.getDatabase(context).fixtureDao())
    }
}