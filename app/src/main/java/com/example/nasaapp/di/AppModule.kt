package com.example.nasaapp.di

import android.content.Context
import androidx.room.Room
import com.example.nasaapp.db.AstronomyDao
import com.example.nasaapp.db.AstronomyDb
import com.example.nasaapp.util.Constant.TABLE_NAME
import com.example.nasaapp.remote.ApiService
import com.example.nasaapp.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getInterceptor() :HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun getOkHttpClient(interceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Singleton
    @Provides
    @Named(Constant.BASE_API_URL)
    fun getBaseUrl():String {
        return Constant.BASE_API_URL
    }

    @Singleton
    @Provides
    fun provideRetrofitService(okHttpClient: OkHttpClient,@Named(Constant.BASE_API_URL)baseUrl:String) : ApiService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideResturantDataBase(@ApplicationContext appContext: Context): AstronomyDb
            = Room.databaseBuilder(appContext, AstronomyDb::class.java,TABLE_NAME).build()

    @Singleton
    @Provides
    fun getResturantDao(db: AstronomyDb): AstronomyDao = db.getResturantDao()

}