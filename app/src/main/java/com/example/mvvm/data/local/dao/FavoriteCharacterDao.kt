package com.example.mvvm.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvm.data.local.entity.FavoriteCharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insert(
        character: FavoriteCharacterEntity
    )

    @Delete
    suspend fun delete(
        character: FavoriteCharacterEntity
    )

    @Query(
        """
        DELETE FROM favorite_characters
        WHERE id = :characterId
        """
    )
    suspend fun deleteById(
        characterId: Int
    )

    @Query(
        """
        SELECT * 
        FROM favorite_characters
        ORDER BY name ASC
        """
    )
    fun getAllFavorites():
            Flow<List<FavoriteCharacterEntity>>

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 
            FROM favorite_characters 
            WHERE id = :characterId
        )
        """
    )
    fun isFavorite(
        characterId: Int
    ): Flow<Boolean>
}