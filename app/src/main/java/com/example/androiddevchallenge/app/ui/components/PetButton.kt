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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.app.ui.theme.BlueGrey200

@Composable
fun PetButton(
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    colors: PetButtonColors = PetButtonDefaults.colors(),
    enabled: Boolean = true,
    elevation: Dp = 4.dp,
) {
    Box(
        modifier
            .padding(elevation.plus(1.dp))
            .shadow(elevation = elevation, shape = MaterialTheme.shapes.medium)
    ) {
        Button(
            onClick = onClick,
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(
                width = 2.dp,
                color = colors.borderColor().value
            ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colors.backgroundColor(enabled = enabled).value,
            ),
            modifier = Modifier
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.button.copy(
                    color = colors.labelColor().value,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

object PetButtonDefaults {
    @Composable
    fun colors(
        backgroundColor: Color = MaterialTheme.colors.primary,
        borderColor: Color = MaterialTheme.colors.background,
        labelColor: Color = MaterialTheme.colors.onPrimary,
        disabledBackgroundColor: Color = BlueGrey200,
    ): PetButtonColors = DefaultButtonColors(
        backgroundColor,
        borderColor,
        disabledBackgroundColor,
        labelColor,
    )
}

@Stable
interface PetButtonColors {
    /**
     * Represents the background color for this button, depending on [enabled].
     *
     * @param enabled whether the button is enabled
     */
    @Composable
    fun backgroundColor(enabled: Boolean): State<Color>

    @Composable
    fun borderColor(): State<Color>

    @Composable
    fun labelColor(): State<Color>
}

@Immutable
private class DefaultButtonColors(
    private val backgroundColor: Color,
    private val borderColor: Color,
    private val disabledBackgroundColor: Color,
    private val labelColor: Color,
) : PetButtonColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) backgroundColor else disabledBackgroundColor)
    }

    @Composable
    override fun borderColor() = rememberUpdatedState(newValue = borderColor)

    @Composable
    override fun labelColor() = rememberUpdatedState(newValue = labelColor)
}
