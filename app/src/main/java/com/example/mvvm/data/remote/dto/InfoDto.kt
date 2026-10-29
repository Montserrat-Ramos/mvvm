package com.example.mvvm.data.remote.dto

data class InfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)