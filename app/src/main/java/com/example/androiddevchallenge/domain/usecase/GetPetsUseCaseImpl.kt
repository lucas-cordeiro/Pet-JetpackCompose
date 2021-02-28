package com.example.androiddevchallenge.domain.usecase

import com.example.androiddevchallenge.domain.model.Pet
import com.example.androiddevchallenge.domain.repository.PetRepository
import com.example.androiddevchallenge.domain.usecase.GetPetsUseCase
import kotlinx.coroutines.flow.Flow

class GetPetsUseCaseImpl(
    private val petRepository: PetRepository,
) : GetPetsUseCase {
    override suspend fun getPets(): Flow<List<Pet>> {
        return petRepository.getPets()
    }

    override suspend fun getPetById(id: String): Flow<Pet?> {
        return petRepository.getPetById(id)
    }
}