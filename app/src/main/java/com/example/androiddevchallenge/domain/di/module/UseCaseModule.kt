package com.example.androiddevchallenge.domain.di.module

import com.example.androiddevchallenge.domain.usecase.GetPetsUseCase
import com.example.androiddevchallenge.domain.usecase.GetPetsUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single { GetPetsUseCaseImpl(get()) as GetPetsUseCase }
}