package com.example.androiddevchallenge.domain.usecase

import com.example.androiddevchallenge.domain.model.Pet
import kotlinx.coroutines.flow.Flow

interface GetPetsUseCase {
    suspend fun getPets(): Flow<List<Pet>>

    suspend fun getPetById(id: String): Flow<Pet?>
}