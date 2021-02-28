package com.example.androiddevchallenge.domain.repository

import com.example.androiddevchallenge.domain.model.Pet
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    suspend fun getPets(): Flow<List<Pet>>

    suspend fun getPetById(id: String): Flow<Pet?>
}