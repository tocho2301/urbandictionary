package com.example.urbandictionary.ui.activity.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.urbandictionary.entity.WordDefinition
import com.example.urbandictionary.entity.worddefinition.repository.WordDefinitionRepository
import com.example.urbandictionary.entity.worddefinition.retrofit.response.GetWordDefinitionsResponse
import io.reactivex.Observable
import org.junit.Before
import org.hamcrest.CoreMatchers.`is`

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class HomeViewModelTest {

    lateinit var SUT : HomeViewModel
    lateinit var mockedWordDefinitionRepository: WordDefinitionRepository

    val WORD_TO_TEST = "TEST"

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockedWordDefinitionRepository = Mockito.mock(WordDefinitionRepository::class.java)
        SUT = HomeViewModel(mockedWordDefinitionRepository)
    }

    @Test
    fun getWordDefinitionsFromServer_functionCalled(){
        Mockito.`when`(mockedWordDefinitionRepository.getDefinitionsFromServer(WORD_TO_TEST)).thenReturn(Observable.just(getWordDefinitionsList()))
        SUT.getDefinitions(WORD_TO_TEST)
        Mockito.verify(mockedWordDefinitionRepository).getDefinitionsFromServer(WORD_TO_TEST)
    }

    @Test
    fun getWordDefinitionsFromCache_functionCalled(){
        Mockito.`when`(mockedWordDefinitionRepository.getDefinitionsFromCache(WORD_TO_TEST)).thenReturn(Observable.just(getWordDefinitionsList()))
        SUT.getLocalDefinitions(WORD_TO_TEST)
        Mockito.verify(mockedWordDefinitionRepository).getDefinitionsFromCache(WORD_TO_TEST)
    }

    fun getWordDefinitionsList() : ArrayList<WordDefinition>{
        return ArrayList<WordDefinition>()
    }

}