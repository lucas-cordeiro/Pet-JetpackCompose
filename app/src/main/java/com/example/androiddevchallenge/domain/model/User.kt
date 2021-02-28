package com.example.androiddevchallenge.domain.model

import com.example.androiddevchallenge.domain.model.Address

data class User(
    var profilePic: Int,
    var name: String,
    var location: Address,
)
