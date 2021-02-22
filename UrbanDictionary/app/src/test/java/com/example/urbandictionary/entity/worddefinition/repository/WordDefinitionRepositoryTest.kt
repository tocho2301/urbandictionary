package com.example.urbandictionary.entity.worddefinition.repository

import androidx.lifecycle.Observer
import com.example.urbandictionary.db.local.WordDefinitionDAO
import com.example.urbandictionary.db.local.WordDefinitonDB
import com.example.urbandictionary.entity.WordDefinition
import com.example.urbandictionary.entity.worddefinition.retrofit.WordDefinitionApi
import com.example.urbandictionary.entity.worddefinition.retrofit.WordDefinitionRetrofitModule
import com.example.urbandictionary.entity.worddefinition.retrofit.response.GetWordDefinitionsResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class WordDefinitionRepositoryTest {

    @Captor
    private lateinit var captor: ArgumentCaptor<String>

    @Mock
    lateinit var mockedWordDefinitonDB: WordDefinitonDB

    @Mock
    lateinit var mockedWordDefinitionRetrofitModule: WordDefinitionRetrofitModule

    @Mock
    lateinit var mockedWordDefinitionApi: WordDefinitionApi.GetWordDefinitions

    @Mock
    lateinit var mockedWordDefinitionDAO : WordDefinitionDAO



    lateinit var SUT : WordDefinitionRepository

    val WORD_TO_TEST = "TEST"


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        Mockito.`when`(mockedWordDefinitionRetrofitModule.getWordDefinitions()).thenReturn(mockedWordDefinitionApi)
        Mockito.`when`(mockedWordDefinitonDB.wordDefinitionDAO()).thenReturn(mockedWordDefinitionDAO)

        SUT = WordDefinitionRepository(mockedWordDefinitionRetrofitModule, mockedWordDefinitonDB)
    }

    @Test
    fun getDefinitionsFromServer_argumentPassed_argumentPassedToRetrofitModule(){
        Mockito.`when`(mockedWordDefinitionApi.getDefinitions(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        )).thenReturn(Observable.just(getMockedResponseEmptyList()))

        SUT.getDefinitionsFromServer(WORD_TO_TEST)

        // Assert
        Mockito.verify(mockedWordDefinitionRetrofitModule).getWordDefinitions()
        Mockito.verify(mockedWordDefinitionApi).getDefinitions(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), captor.capture())

        assertThat(captor.value, CoreMatchers.`is`(WORD_TO_TEST))
    }

    @Test
    fun `getDefinitonsFromServer emptyListReturnedFromApi ErrorObservavbleReturned`(){
        Mockito.`when`(mockedWordDefinitionApi.getDefinitions(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        )).thenReturn(Observable.just(getMockedResponseEmptyList()))

        SUT.getDefinitionsFromServer(WORD_TO_TEST)


        val observable: Observable<ArrayList<WordDefinition>> = SUT.getDefinitionsFromServer(WORD_TO_TEST)
        val testSubscriber: TestObserver<ArrayList<WordDefinition>> = TestObserver<ArrayList<WordDefinition>>()
        observable.subscribe(testSubscriber)

        //Assert
        testSubscriber.awaitDone(5, TimeUnit.SECONDS).assertErrorMessage("We couldn´t find any definition for $WORD_TO_TEST")
    }

    @Test
    fun `getDefinitonsFromServer fullListReturnedFromApi ArrayListObservableReturned`(){
        Mockito.`when`(mockedWordDefinitionApi.getDefinitions(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        )).thenReturn(Observable.just(getMockedResponseFullList()))

        SUT.getDefinitionsFromServer(WORD_TO_TEST)


        val observable: Observable<ArrayList<WordDefinition>> = SUT.getDefinitionsFromServer(WORD_TO_TEST)
        val testSubscriber: TestObserver<ArrayList<WordDefinition>> = TestObserver<ArrayList<WordDefinition>>()
        observable.subscribe(testSubscriber)


        //Assert
        testSubscriber.awaitDone(5, TimeUnit.SECONDS).assertComplete()
        testSubscriber.awaitDone(5, TimeUnit.SECONDS).assertValue { arr ->
            arr.size > 0
        }
        testSubscriber.awaitDone(5,TimeUnit.SECONDS).assertNoErrors()


    }

    @Test
    fun saveDefinitionsInCache_dataSaved_ObservableReturned(){
        Mockito.`when`(mockedWordDefinitionDAO.saveWordDefinitionList(ArgumentMatchers.anyList())).thenReturn(Completable.complete())

        val observable: Observable<Int> = SUT.saveDefinitonInCache(ArrayList<WordDefinition>())
        val testSubscriber: TestObserver<Int> = TestObserver<Int>()

        observable.subscribe(testSubscriber)

        testSubscriber.awaitDone(5,TimeUnit.SECONDS).assertNoErrors()
        testSubscriber.awaitDone(5,TimeUnit.SECONDS).assertComplete()

    }

    @Test
    fun getDefinitionsFromCache_successfullresponse_returnObservableWithWordDefinitions(){
        Mockito.`when`(mockedWordDefinitionDAO.searchWordDefinition(ArgumentMatchers.anyString())).thenReturn(Observable.just(getMockedFullList()))

        val observavble = SUT.getDefinitionsFromCache(WORD_TO_TEST)

        observavble.test().awaitDone(5,TimeUnit.SECONDS).assertComplete()
        observavble.test().awaitDone(5,TimeUnit.SECONDS).assertNoErrors()
    }

    fun getMockedResponseEmptyList() : GetWordDefinitionsResponse{
        return GetWordDefinitionsResponse(
                ArrayList<WordDefinition>(),
                "ErrorMessage"
        )

    }

    fun getMockedResponseFullList() : GetWordDefinitionsResponse{
        return GetWordDefinitionsResponse(
                getMockedFullList(),
                "ErrorMessage"
        )
    }

    fun getMockedFullList() : ArrayList<WordDefinition>{
        val wordDefinitionList = ArrayList<WordDefinition>()
        val wordDefinition = WordDefinition()
        wordDefinition.word = "WordResult1"
        wordDefinition.definition = "Test1"
        wordDefinitionList.add(wordDefinition)

        wordDefinition.word = "WordResult2"
        wordDefinition.definition = "Test2"
        wordDefinitionList.add(wordDefinition)

        return wordDefinitionList
    }

}