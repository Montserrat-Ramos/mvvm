package com.example.mvvm.di

import com.example.mvvm.data.repository.CharacterRepositoryImpl
import com.example.mvvm.data.repository.FavoriteRepositoryImpl
import com.example.mvvm.domain.repository.CharacterRepository
import com.example.mvvm.domain.repository.FavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        implementation: CharacterRepositoryImpl
    ): CharacterRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(
        implementation: FavoriteRepositoryImpl
    ): FavoriteRepository
}