package com.example.mvvm.di

import android.content.Context
import androidx.room.Room
import com.example.mvvm.data.local.dao.FavoriteCharacterDao
import com.example.mvvm.data.local.databese.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rick_morty_database"
        ).build()
    }

    @Provides
    fun provideFavoriteCharacterDao(
        database: AppDatabase
    ): FavoriteCharacterDao {

        return database.favoriteCharacterDao()
    }
}