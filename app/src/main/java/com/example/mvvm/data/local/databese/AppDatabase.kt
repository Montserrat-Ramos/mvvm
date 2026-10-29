package com.example.mvvm.data.local.databese

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvm.data.local.dao.FavoriteCharacterDao
import com.example.mvvm.data.local.entity.FavoriteCharacterEntity

@Database(
    entities = [
        FavoriteCharacterEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase :
    RoomDatabase() {

    abstract fun favoriteCharacterDao():
            FavoriteCharacterDao
}