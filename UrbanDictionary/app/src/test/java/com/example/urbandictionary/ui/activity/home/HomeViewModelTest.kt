package com.example.urbandictionary.ui.activity.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.urbandictionary.entity.worddefinition.WordDefinition
import com.example.urbandictionary.entity.worddefinition.repository.WordDefinitionRepository
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.hamcrest.CoreMatchers.`is`

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    lateinit var SUT : HomeViewModel

    val WORD_TO_TEST = "TEST"
    val ERROR_MESSAGE = "ERROR FOUND"


    @Mock
    lateinit var mockedWordDefinitionRepository: WordDefinitionRepository

    @Mock
    lateinit var mockedProgressBarObserver: Observer<Boolean>

    @Mock
    lateinit var mockedErrorObserver: Observer<String>

    @Mock
    lateinit var mockedShowResultLayoutObserver : Observer<Boolean>

    @Mock
    lateinit var mockerOrderOptionsDialogObserver: Observer<Boolean>

    @Mock
    lateinit var mockedOrderListBy : Observer<Int>

    @Mock
    lateinit var mockedChangeOrderByTextObserver: Observer<String >

    @Mock
    lateinit var mockedWordDefinitionListObserver: Observer<ArrayList<WordDefinition>>

    @Captor
    private lateinit var captor: ArgumentCaptor<Boolean>

    @Captor
    private lateinit var orderByCaptor : ArgumentCaptor<Int>

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler{ Schedulers.trampoline()}
        SUT = HomeViewModel(mockedWordDefinitionRepository)
    }

    @Test
    fun getWordDefinitionsFromServer_functionCalled(){
        Mockito.`when`(mockedWordDefinitionRepository.getDefinitionsFromServer(WORD_TO_TEST)).thenReturn(Observable.just(getFullWordDefinitionsList()))
        SUT.getDefinitions(WORD_TO_TEST)
        Mockito.verify(mockedWordDefinitionRepository).getDefinitionsFromServer(WORD_TO_TEST)
    }

    @Test
    fun getWordDefinitionsFromCache_functionCalled(){
        Mockito.`when`(mockedWordDefinitionRepository.getDefinitionsFromCache(WORD_TO_TEST)).thenReturn(Observable.just(getFullWordDefinitionsList()))
        SUT.getLocalDefinitions(WORD_TO_TEST)
        Mockito.verify(mockedWordDefinitionRepository).getDefinitionsFromCache(WORD_TO_TEST)
    }

    @Test
    fun getDefinitionsFromServerSuccessFullResponseDefinitionsLiveDataValueChanged(){

        Mockito.`when`(mockedWordDefinitionRepository.getDefinitionsFromServer(ArgumentMatchers.anyString())).thenReturn(
            Observable.just(getFullWordDefinitionsList()))

        Mockito.`when`(mockedWordDefinitionRepository.saveDefinitonInCache(getFullWordDefinitionsList())).thenReturn(Observable.just(1))

        SUT.showProgressBar.observeForever(mockedProgressBarObserver)
        SUT.errorMesage.observeForever(mockedErrorObserver)
        SUT.showResultLayout.observeForever(mockedShowResultLayoutObserver)
        SUT.definitonsList.observeForever(mockedWordDefinitionListObserver)
        SUT.changeOrderByText.observeForever(mockedChangeOrderByTextObserver)
        SUT.getDefinitions(WORD_TO_TEST)

        Mockito.verify(mockedShowResultLayoutObserver, times(2)).onChanged(ArgumentMatchers.anyBoolean())
        Mockito.verify(mockedChangeOrderByTextObserver, times(1)).onChanged(ArgumentMatchers.anyString())
        Mockito.verify(mockedWordDefinitionRepository).saveDefinitonInCache(getFullWordDefinitionsList())
        Mockito.verify(mockedProgressBarObserver, Mockito.times(2)).onChanged(captor.capture())
        Mockito.verify(mockedWordDefinitionListObserver, times(1)).onChanged(getFullWordDefinitionsList())
        Mockito.verifyZeroInteractions(mockedErrorObserver)

        assertThat(captor.allValues[0], `is`(true))
        assertThat(captor.allValues[1], `is`(false))
    }

    @Test
    fun getDefinitionsFromServer_FailureResponse_ErrorValueChanged(){

        Mockito.`when`(mockedWordDefinitionRepository.getDefinitionsFromServer(ArgumentMatchers.anyString())).thenReturn(
                Observable.error(Throwable("Error")))

        SUT.showProgressBar.observeForever(mockedProgressBarObserver)
        SUT.errorMesage.observeForever(mockedErrorObserver)
        SUT.definitonsList.observeForever(mockedWordDefinitionListObserver)

        SUT.getDefinitions(WORD_TO_TEST)

        Mockito.verifyNoMoreInteractions(mockedWordDefinitionListObserver)
        Mockito.verify(mockedProgressBarObserver, Mockito.times(2)).onChanged(captor.capture())
        Mockito.verify(mockedErrorObserver, times(1)).onChanged(any())

    }

    @Test
    fun getLocalDefinitions_functionCalled_successFullResponseFetched(){
        Mockito.`when`(mockedWordDefinitionRepository.getDefinitionsFromCache(anyString())).thenReturn(Observable.just(getFullWordDefinitionsList()))

        SUT.showProgressBar.observeForever(mockedProgressBarObserver)
        SUT.errorMesage.observeForever(mockedErrorObserver)
        SUT.showResultLayout.observeForever(mockedShowResultLayoutObserver)
        SUT.definitonsList.observeForever(mockedWordDefinitionListObserver)
        SUT.changeOrderByText.observeForever(mockedChangeOrderByTextObserver)

        SUT.getLocalDefinitions(WORD_TO_TEST)

        Mockito.verify(mockedWordDefinitionRepository).getDefinitionsFromCache(WORD_TO_TEST)

        Mockito.verify(mockedShowResultLayoutObserver, times(2)).onChanged(ArgumentMatchers.anyBoolean())
        Mockito.verify(mockedChangeOrderByTextObserver, times(1)).onChanged(ArgumentMatchers.anyString())
        Mockito.verify(mockedWordDefinitionRepository, times(0)).saveDefinitonInCache(getFullWordDefinitionsList())
        Mockito.verify(mockedProgressBarObserver, Mockito.times(2)).onChanged(captor.capture())
        Mockito.verify(mockedWordDefinitionListObserver, times(1)).onChanged(getFullWordDefinitionsList())
        Mockito.verifyZeroInteractions(mockedErrorObserver)

    }

    @Test
    fun getLocalDefinitions_functionCalled_FailureResponseFetched(){
        Mockito.`when`(mockedWordDefinitionRepository.getDefinitionsFromCache(anyString())).thenReturn(Observable.error(Throwable(ERROR_MESSAGE)))

        SUT.showProgressBar.observeForever(mockedProgressBarObserver)
        SUT.errorMesage.observeForever(mockedErrorObserver)
        SUT.definitonsList.observeForever(mockedWordDefinitionListObserver)

        SUT.getLocalDefinitions(WORD_TO_TEST)

        Mockito.verifyNoMoreInteractions(mockedWordDefinitionListObserver)
        Mockito.verify(mockedProgressBarObserver, Mockito.times(2)).onChanged(ArgumentMatchers.anyBoolean())
        Mockito.verify(mockedErrorObserver, times(1)).onChanged(ERROR_MESSAGE)



    }

    @Test
    fun openOrderByButtonClicked_functionCalled_OrderByLiveDataValueChanged(){
        SUT.openOrderOptionsDialog.observeForever(mockerOrderOptionsDialogObserver)

        SUT.onOrderByButtonClicked()

        Mockito.verify(mockerOrderOptionsDialogObserver).onChanged(true)
    }

    @Test
    fun onOrderByThumbsUp_functionCalled_LiveDataValueChanged(){
        SUT.orderListBy.observeForever(mockedOrderListBy)
        SUT.changeOrderByText.observeForever(mockedChangeOrderByTextObserver)

        SUT.onOrderByThumbsUp()

        Mockito.verify(mockedOrderListBy).onChanged(0)
        Mockito.verify(mockedChangeOrderByTextObserver).onChanged("More thumbs up first")
    }

    @Test
    fun onOrderByThumbsDown_functionCalled_LiveDataValueChanged(){
        SUT.orderListBy.observeForever(mockedOrderListBy)
        SUT.changeOrderByText.observeForever(mockedChangeOrderByTextObserver)

        SUT.onOrderByThumbsDown()

        Mockito.verify(mockedOrderListBy).onChanged(1)
        Mockito.verify(mockedChangeOrderByTextObserver).onChanged("More thumbs down first")
    }

    @Test
    fun saveDefinitionsListInCache_functionCalled_listPassedToMethod(){
        Mockito.`when`(mockedWordDefinitionRepository.saveDefinitonInCache(getFullWordDefinitionsList())).thenReturn(Observable.just(1))
        SUT.saveDefinitionsListInCache(getFullWordDefinitionsList())
        Mockito.verify(mockedWordDefinitionRepository).saveDefinitonInCache(getFullWordDefinitionsList())
    }

    private fun getEmptyWordDefinitionsList() : ArrayList<WordDefinition>{
        return ArrayList<WordDefinition>()
    }

    private fun getFullWordDefinitionsList() : ArrayList<WordDefinition>{
        var result = ArrayList<WordDefinition>()
        var wordDefinition =  WordDefinition()
        wordDefinition.word = "Example"
        wordDefinition.definition = "definition"
        result.add(wordDefinition)
        return result
    }

}