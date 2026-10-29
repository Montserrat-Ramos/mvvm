package com.example.mvvm.data.remote.dto

import com.example.mvvm.domain.model.CharacterModel

fun CharacterDto.toDomain(): CharacterModel {

    return CharacterModel(
        id = id,
        name = name,
        status = status,
        species = species,
        image = image,
        gender = gender,
        origin = origin.name,
        location = location.name
    )
}