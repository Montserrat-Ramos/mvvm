package com.example.mvvm.data.remote.dto

data class CharacterResponseDto(
    val info: InfoDto,
    val results: List<CharacterDto>
)