package com.example.mvvm.di

import com.example.mvvm.data.remote.api.RickMortyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(
                "https://rickandmortyapi.com/api/"
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRickMortyApi(
        retrofit: Retrofit
    ): RickMortyApi {

        return retrofit.create(
            RickMortyApi::class.java
        )
    }
}