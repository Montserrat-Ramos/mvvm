package com.example.mvvm.data.local.mapper

import com.example.mvvm.data.local.entity.FavoriteCharacterEntity
import com.example.mvvm.domain.model.CharacterModel

fun CharacterModel.toFavoriteEntity():
        FavoriteCharacterEntity {

    return FavoriteCharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        image = image,
        gender = gender,
        origin = origin,
        location = location
    )
}

fun FavoriteCharacterEntity.toDomain():
        CharacterModel {

    return CharacterModel(
        id = id,
        name = name,
        status = status,
        species = species,
        image = image,
        gender = gender,
        origin = origin,
        location = location
    )
}