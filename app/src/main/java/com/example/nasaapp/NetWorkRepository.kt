package com.example.nasaapp

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*

abstract class NetWorkRepository<RESULT, REQUEST> {

    val asFlow = flow<State<RESULT>> {
        emit(State.loading())
        val response = fetchFromRemote()
        Log.d("TAG", "Response is  $response")
        saveLocally(response)
        emitAll(fetchFromLocal().map {
            State.success<RESULT>(it)
        })
    }

     abstract suspend fun saveLocally(response: Response)

     abstract fun fetchFromLocal(): Flow<RESULT>

     abstract suspend fun fetchFromRemote(): Response
}