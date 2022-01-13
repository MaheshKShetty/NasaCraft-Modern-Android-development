package com.example.nasaapp.remote

import com.example.nasaapp.db.AstronomyDao
import com.example.nasaapp.model.Response
import com.example.nasaapp.model.State
import com.example.nasaapp.util.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService, private val astronomyDao: AstronomyDao) {

   fun getAstronomyData(): Flow<State<Response>> {
        return object : BaseRepo <Response, Response>() {

            override suspend fun fetchFromRemote(): Response {
                return getApiResponse()
            }

            override suspend fun saveLocally(response: Response) {
                astronomyDao.insert(response)
            }

            override fun fetchFromLocal(): Flow<Response> {
                return astronomyDao.getAstronomy()
            }

            override suspend fun getLocalData(): Flow<Response?> {
                return astronomyDao.getAstronomy()
            }

        }.asFlow.flowOn(Dispatchers.IO)
    }

    suspend fun getApiResponse(): Response {
        return apiService.getAstronomyInfo(Constant.API_KEY)
    }
}