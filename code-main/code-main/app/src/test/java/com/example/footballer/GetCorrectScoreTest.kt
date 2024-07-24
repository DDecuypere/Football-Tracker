package com.example.footballer

import com.example.footballer.model.FullTime
import com.example.footballer.model.HalfTime
import com.example.footballer.ui.screens.home.checkCurrentScore
import com.example.footballer.ui.screens.home.overallScore
import org.junit.Assert
import org.junit.Test

class GetCorrectScoreTest {
    @Test
    fun `get overall score with both scores filled in` () {
        val halfTimeScore = HalfTime(home = 1, away = 2)
        val fullTimeScore = FullTime(home = 3, away = 4)

        val result = overallScore(halfTimeScore, fullTimeScore)

        Assert.assertEquals("3 - 4", result)
    }

    @Test
    fun `get overall score with both scores not filled in` () {
        val halfTimeScore = HalfTime(home = null, away = null)
        val fullTimeScore = FullTime(home = null, away = null)

        val result = overallScore(halfTimeScore, fullTimeScore)

        Assert.assertEquals("", result)
    }

    @Test
    fun `get overall score with only first half score` () {
        val halfTimeScore = HalfTime(home = 1, away = 2)
        val fullTimeScore = FullTime(home = null, away = null)

        val result = overallScore(halfTimeScore, fullTimeScore)

        Assert.assertEquals("1 - 2", result)
    }

    @Test
    fun `get current score both filled in` () {
        val halfTime = 1
        val fullTime = 2

        val result = checkCurrentScore(halfTime, fullTime)

        Assert.assertEquals(2, result)
    }

    @Test
    fun `get current score with only first half` (){
        val halfTime = 1
        val fullTime = null

        val result = checkCurrentScore(halfTime, fullTime)

        Assert.assertEquals(1, result)
    }
}