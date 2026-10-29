package com.example.mvvm.data.remote.api

import com.example.mvvm.data.remote.dto.CharacterDto
import com.example.mvvm.data.remote.dto.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickMortyApi {
    @GET("character")
    suspend fun getCharacters(): CharacterResponseDto

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharacterResponseDto

    @GET("character")
    suspend fun searchCharacters(
        @Query("name") name: String,
        @Query("page") page: Int
    ): CharacterResponseDto

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): CharacterDto
}