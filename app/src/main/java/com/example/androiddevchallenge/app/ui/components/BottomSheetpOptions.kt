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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.app.ui.theme.PetTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetOptions(
    options: List<BottomSheetOption>,
    onClick: (Int) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    dark: Boolean = !MaterialTheme.colors.isLight,
) {
    PetTheme(darkTheme = dark) {
        Column(modifier) {
            Box(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column {
                    options.forEachIndexed { index, option ->
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { onClick(index) }, indication = null, interactionSource = MutableInteractionSource())
                                .padding(vertical = 8.dp)
                                .height(40.dp)
                        ) {
                            if (option.icon != null)
                                OptionsIcon(
                                    icon = option.icon,
                                    tint = option.color,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(horizontal = 8.dp)
                                )
                            if (!option.label.isNullOrBlank())
                                OptionsLabel(
                                    label = option.label ?: "",
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(horizontal = 8.dp)
                                )
                        }
                        Divider(modifier = Modifier.padding(horizontal = 24.dp))
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 4.dp)
            ) {
                BackLabel(
                    label = "Voltar",
                    onClick = onClose,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun OptionsIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector?,
    tint: Color?,
) {
    Box(modifier = modifier) {
        if (icon != null)
            Icon(
                imageVector = icon,
                contentDescription = "Ícone da opção",
                tint = tint ?: MaterialTheme.colors.primary,
                modifier = Modifier
                    .size(32.dp)
            )
    }
}

@Composable
private fun OptionsLabel(
    label: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Text(
            text = label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h5.copy(
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun BackLabel(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(40.dp)
            .clickable(onClick = onClick, interactionSource = MutableInteractionSource(), indication = null)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.h5.copy(
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

data class BottomSheetOption(
    var label: String? = null,
    var icon: ImageVector? = null,
    var color: Color? = null
)
