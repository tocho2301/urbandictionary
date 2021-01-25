package com.example.urbandictionary.ui.activity.home

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.urbandictionary.entity.WordDefinition
import com.example.urbandictionary.entity.worddefinition.repository.WordDefinitionRepository

class HomeViewModel @ViewModelInject constructor(
    var wordDefinitionRepository: WordDefinitionRepository
) : ViewModel() {

    private val _showDefinitions = MutableLiveData<ArrayList<WordDefinition>>()
    val showDefinitions : LiveData<ArrayList<WordDefinition>> get() = _showDefinitions

    private val _showError = MutableLiveData<String>()
    val showError : LiveData<String> get() = _showError

    private val _showProgressBar = MutableLiveData<Boolean>()
    val showProgressBar : LiveData<Boolean> get() = _showProgressBar

    private val _showResultLayout = MutableLiveData<Boolean>()
    val showResultLayout : LiveData<Boolean> get() = _showResultLayout

    private val _openOrderOptionsDialog = MutableLiveData<Boolean>()
    val  openOrderOptionsDialog : LiveData<Boolean> get() = _openOrderOptionsDialog

    private val _orderListBy = MutableLiveData<Int>()
    val orderListBy : LiveData<Int> get() = _orderListBy

    private val _changeORderByText = MutableLiveData<String>()
    val changeOrderByText : LiveData<String> get() = _changeORderByText


    @SuppressLint("CheckResult")
    fun getDefinitions(word : String){
        _showProgressBar.value = true
        _showResultLayout.value = false
        wordDefinitionRepository.getDefinitionsFromServer(word)
            .subscribe({ wordDefinitions ->
                _showProgressBar.postValue(false)
                _showResultLayout.postValue(true)
                _showDefinitions.postValue(wordDefinitions)
                saveDefinitionsListInCache(wordDefinitions)
                _changeORderByText.value = "Default"
            },{ throwable ->
                _showProgressBar.postValue(false)
                _showError.postValue(throwable.message)
            })
    }

    @SuppressLint("CheckResult")
    fun getLocalDefinitions(word : String){
        _showProgressBar.value = true
        _showResultLayout.value = false
        wordDefinitionRepository.getDefinitionsFromCache(word)
            .subscribe({ wordDefinitions ->
                if(wordDefinitions.size>0) {
                    _showProgressBar.postValue(false)
                    _showResultLayout.postValue(true)
                    _showDefinitions.postValue(wordDefinitions)
                    _changeORderByText.value = "Default"
                }
            },{ throwable ->
                _showProgressBar.postValue(false)
                _showError.postValue(throwable.message)
            })
    }

    @SuppressLint("CheckResult")
    fun saveDefinitionsListInCache(list: ArrayList<WordDefinition>){
        wordDefinitionRepository.saveDefinitonInCache(list)
            .subscribe ({}, {})
    }

    fun onOrderByButtonClicked() {
        _openOrderOptionsDialog.value = true
    }

    fun onOrderByThumbsUp() {
        _orderListBy.value = 0
        _changeORderByText.value = "More thumbs up first"
    }

    fun onOrderByThumbsDown() {
        _orderListBy.value = 1
        _changeORderByText.value = "More thumbs down first"
    }

}