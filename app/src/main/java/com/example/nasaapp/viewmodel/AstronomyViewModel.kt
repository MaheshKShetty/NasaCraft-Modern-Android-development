package com.example.nasaapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapp.DBDeleteWorkManager
import com.example.nasaapp.remote.Repository
import com.example.nasaapp.model.Response
import com.example.nasaapp.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AstronomyViewModel @Inject constructor(private val repository: Repository, val dbDeleteWorkManager: DBDeleteWorkManager): ViewModel() {

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

    fun clearLocalData(context: Context) {
        // here based on time if time is 12 then we can create a work to delete the data from database
        dbDeleteWorkManager.createTaskToDeleteDb(context)
    }

}