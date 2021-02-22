package com.example.urbandictionary.ui.activity.splash

import androidx.arch.core.executor.TaskExecutor
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.doAnswer
import java.util.*


class SplashViewModelTest {

    lateinit var mockedTimer : Timer
    lateinit var SUT : SplashViewModel

    val DELAY : Long = 3000

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockedTimer = Mockito.mock(Timer::class.java)
        SUT = SplashViewModel(mockedTimer)
    }

    @Test
    fun doSplash_functionCalled_executeScheduleMethodFromTimer(){
        SUT.doSplash()
        Mockito.verify(mockedTimer).schedule(
            ArgumentMatchers.any(TimerTask::class.java), ArgumentMatchers.eq(
                DELAY
            )
        )
    }
}