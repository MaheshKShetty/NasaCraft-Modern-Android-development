package com.example.nasaapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AstronomyViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    var astronomyData =  MutableLiveData<State<Response>>()

    private val exceptionHandler = CoroutineExceptionHandler { _,exception ->
        astronomyData.postValue(State.Error(exception.message.toString()))
    }

    fun getAstronomyResponse() {
        viewModelScope.launch(Dispatchers.IO +exceptionHandler ) {
            repository.getAstronomyData().collect { data ->
                astronomyData.postValue(data)
            }
        }
    }

}