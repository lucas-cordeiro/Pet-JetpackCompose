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
package com.example.androiddevchallenge.app.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.app.ui.theme.PetTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetController(
    modifier: Modifier = Modifier,
    bottomSheetContent: @Composable (expandedBottomSheet: () -> Unit, hiddenBottomSheet: (() -> Unit) -> Unit) -> Unit,
    dark: Boolean = !MaterialTheme.colors.isLight,
    content: @Composable (expandedBottomSheet: () -> Unit, hiddenBottomSheet: (() -> Unit) -> Unit) -> Unit,
) {

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scrimColor = Color.Black.copy(alpha = 0.37f)

    var onHidden by remember { mutableStateOf({}) }
    val scope = rememberCoroutineScope()
    val hiddenBottomSheet: (() -> Unit) -> Unit = {
        scope.launch {
            try {
                onHidden = it
                sheetState.animateTo(ModalBottomSheetValue.Hidden)
            } catch (t: Throwable) {
            }
        }
    }

    val expandedBottomSheet = {
        scope.launch {
            delay(100)
            sheetState.animateTo(ModalBottomSheetValue.Expanded)
        }
    }

    DisposableEffect(sheetState.currentValue) {
        when (sheetState.currentValue) {
            ModalBottomSheetValue.Hidden -> {
                onHidden()
                onHidden = {}
            }
            ModalBottomSheetValue.Expanded -> {
            }
            else -> {
            }
        }
        onDispose { }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            PetTheme(darkTheme = dark) {
                bottomSheetContent(expandedBottomSheet, hiddenBottomSheet)
            }
        },
        content = { content(expandedBottomSheet, hiddenBottomSheet) },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        scrimColor = scrimColor,
        modifier = modifier
    )
}
