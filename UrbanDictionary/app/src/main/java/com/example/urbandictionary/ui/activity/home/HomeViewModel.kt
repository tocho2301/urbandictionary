package com.example.urbandictionary.ui.activity.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.urbandictionary.entity.worddefinition.WordDefinition
import com.example.urbandictionary.entity.worddefinition.repository.WordDefinitionRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(
   private var wordDefinitionRepository: WordDefinitionRepository
) : ViewModel() {

    private val _definitonsList = MutableLiveData<ArrayList<WordDefinition>>()
    val definitonsList : LiveData<ArrayList<WordDefinition>> get() = _definitonsList

    private val _errorMesage = MutableLiveData<String>()
    val errorMesage : LiveData<String> get() = _errorMesage

    private val _showProgressBar = MutableLiveData<Boolean>()
    val showProgressBar : LiveData<Boolean> get() = _showProgressBar

    private val _showResultLayout = MutableLiveData<Boolean>()
    val showResultLayout : LiveData<Boolean> get() = _showResultLayout

    private val _openOrderOptionsDialog = MutableLiveData<Boolean>()
    val  openOrderOptionsDialog : LiveData<Boolean> get() = _openOrderOptionsDialog

    private val _orderListBy = MutableLiveData<Int>()
    val orderListBy : LiveData<Int> get() = _orderListBy

    private val _changeOrderByText = MutableLiveData<String>()
    val changeOrderByText : LiveData<String> get() = _changeOrderByText

    var disposableList = CompositeDisposable()


    fun getDefinitions(word : String){
        _showProgressBar.value = true
        _showResultLayout.value = false
        disposableList.add(wordDefinitionRepository.getDefinitionsFromServer(word)
                .subscribe({ wordDefinitions ->
                    saveDefinitionsListInCache(wordDefinitions)
                    _showResultLayout.postValue(true)
                    _definitonsList.postValue(wordDefinitions)
                    _changeOrderByText.postValue("Default")
                    _showProgressBar.postValue(false)

                },{ throwable ->
                    _showProgressBar.postValue(false)
                    _errorMesage.postValue(throwable.message)
                }))
    }

    fun getLocalDefinitions(word : String){
        _showProgressBar.value = true
        _showResultLayout.value = false
        disposableList.add(wordDefinitionRepository.getDefinitionsFromCache(word)
            .subscribe({ wordDefinitions ->
                if(wordDefinitions.size>0) {
                    _showProgressBar.postValue(false)
                    _showResultLayout.postValue(true)
                    _definitonsList.postValue(wordDefinitions)
                    _changeOrderByText.value = "Default"
                }
            },{ throwable ->
                _showProgressBar.postValue(false)
                _errorMesage.postValue(throwable.message)
            }))
    }

    fun saveDefinitionsListInCache(list: ArrayList<WordDefinition>){
        disposableList.add(wordDefinitionRepository.saveDefinitonInCache(list)
            .subscribe ())
    }

    fun onOrderByButtonClicked() {
        _openOrderOptionsDialog.value = true
    }

    fun onOrderByThumbsUp() {
        _orderListBy.value = 0
        _changeOrderByText.value = "More thumbs up first"
    }

    fun onOrderByThumbsDown() {
        _orderListBy.value = 1
        _changeOrderByText.value = "More thumbs down first"
    }

    override fun onCleared() {
        super.onCleared()
        disposableList.clear()
    }

}