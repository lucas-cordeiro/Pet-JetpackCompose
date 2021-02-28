/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.app.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.domain.model.Category
import com.example.androiddevchallenge.domain.model.Gender
import com.example.androiddevchallenge.domain.model.Pet
import com.example.androiddevchallenge.domain.usecase.GetPetsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPetsUseCase: GetPetsUseCase
) : ViewModel() {

    private var _pets: List<Pet> by mutableStateOf(emptyList())

    var pets: List<Pet> by mutableStateOf(emptyList())

    var category: Category? by mutableStateOf(null)
        private set

    var gender: Gender? by mutableStateOf(null)
        private set

    init {
        collectPets()
    }

    private fun collectPets() {
        viewModelScope.launch {
            getPetsUseCase.getPets().collect {
                _pets = it
                pets = _pets
            }
        }
    }

    fun updateCategory(category: Category?) {
        this.category = category
        applyFilter()
    }

    fun updateGender(gender: Gender?) {
        if (gender != this.gender) {
            this.gender = gender
        } else {
            this.gender = null
        }
        applyFilter()
    }

    fun clearFilter() {
        gender = null
        category = null
        applyFilter()
    }

    private fun applyFilter() {
        viewModelScope.launch {
            pets = _pets.filter {
                val correctCategory = if (category != null) it.category == category else true
                val correctGender = if (gender != null) it.gender == gender else true
                correctCategory && correctGender
            }
        }
    }
}
