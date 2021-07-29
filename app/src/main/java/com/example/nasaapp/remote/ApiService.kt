package com.example.nasaapp.remote

import com.example.nasaapp.model.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("user_key: 6032a11194ebc2eb4580842cad190e22")
    @GET("planetary/apod?")
    suspend fun getAstronomyInfo(@Query("api_key") queryParameters1: String) : Response


}