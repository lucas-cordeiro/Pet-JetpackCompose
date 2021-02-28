package com.example.androiddevchallenge.domain.model

data class Pet(
    var id: String,
    var name: String,
    var description: String,
    var category: Category,
    var gender: Gender,
    var birthDate: Long,
    var profilePic: Int,
    var location: Address,
    var user: User,
)
