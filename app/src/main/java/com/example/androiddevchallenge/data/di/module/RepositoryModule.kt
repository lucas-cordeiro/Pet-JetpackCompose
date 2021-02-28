package com.example.androiddevchallenge.data.di.module

import com.example.androiddevchallenge.data.repository.PetRepositoryImpl
import com.example.androiddevchallenge.domain.repository.PetRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PetRepositoryImpl() as PetRepository }
}