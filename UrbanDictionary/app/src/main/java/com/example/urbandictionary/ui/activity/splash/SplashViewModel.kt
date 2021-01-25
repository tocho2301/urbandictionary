package com.example.urbandictionary.ui.activity.splash
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.concurrent.schedule


class SplashViewModel @ViewModelInject constructor(
     var timer: Timer
): ViewModel() {

    private val _openHomeActivity = MutableLiveData<Boolean>()
    val openHomeActivity : LiveData<Boolean> get() = _openHomeActivity

    fun doSplash(){
        timer.schedule(3000){
            _openHomeActivity.postValue(true)
        }
    }
}