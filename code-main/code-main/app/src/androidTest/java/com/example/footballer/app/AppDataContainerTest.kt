package com.example.footballer.app

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.workDataOf
import com.example.footballer.data.AppContainer
import com.example.footballer.data.AppDataContainer
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDataContainerTest {
    private lateinit var container : AppContainer
    private lateinit var context : Context

    @Before
    fun setup(){
        context = ApplicationProvider.getApplicationContext()
        container = AppDataContainer(context)
    }

    @Test
    fun testFootballerRepositoryInitialized() {
        assertNotNull(container.footballerRepository)
    }

    @Test
    fun testOfflineFootballerRepositoryInitialized() {
        assertNotNull(container.offlineFootballerRepository)
    }
}