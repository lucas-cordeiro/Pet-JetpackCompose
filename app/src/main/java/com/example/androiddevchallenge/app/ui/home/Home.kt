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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.app.di.helper.getViewModel
import com.example.androiddevchallenge.app.ui.components.BottomSheetController
import com.example.androiddevchallenge.app.ui.components.PetButton
import com.example.androiddevchallenge.app.ui.components.PetButtonDefaults
import com.example.androiddevchallenge.app.ui.theme.BlueGrey100
import com.example.androiddevchallenge.app.ui.theme.DeepPurple200
import com.example.androiddevchallenge.app.ui.theme.Purple200
import com.example.androiddevchallenge.app.utils.formattedDate
import com.example.androiddevchallenge.domain.model.Category
import com.example.androiddevchallenge.domain.model.Gender
import com.example.androiddevchallenge.domain.model.Pet
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.systemBarsPadding

@Composable
fun Home(
    openPetDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = getViewModel(),
) {
    BottomSheetController(
        bottomSheetContent = { expandedBottomSheet, hiddenBottomSheet ->
            BottomSheetGenderContent(
                gender = viewModel.gender,
                onSelectGender = { viewModel.updateGender(it) },
                hiddenBottomSheet = { hiddenBottomSheet {} },
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
            )
        },
        modifier = modifier
    ) { expandedBottomSheet, hiddenBottomSheet ->
        Column(Modifier.systemBarsPadding()) {

            Header(
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Banner(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )

            Filter(
                openGenderSelect = expandedBottomSheet,
                category = viewModel.category,
                gender = viewModel.gender,
                onSelectCategory = { viewModel.updateCategory(it) },
                clearFilter = { viewModel.clearFilter() },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )

            Crossfade(
                targetState = viewModel.pets,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing,
                ),
            ) { pets ->
                LazyColumn(
                    modifier = Modifier
                ) {
                    itemsIndexed(pets) { index, pet ->
                        PetEntity(
                            pet = pet,
                            onClick = { openPetDetail(pet.id) },
                            modifier = Modifier
                                .padding(
                                    top = 16.dp,
                                    bottom = if (index < pets.size - 1) 0.dp else 16.dp
                                )
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomSheetGenderContent(
    gender: Gender?,
    onSelectGender: (Gender?) -> Unit,
    hiddenBottomSheet: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        Box(Modifier.fillMaxWidth()) {
            Spacer(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.onSurface.copy(
                            alpha = 0.08f
                        ),
                        shape = RoundedCornerShape(2.dp)
                    )
                    .width(40.dp)
                    .height(4.dp)
                    .align(Alignment.Center)
            )
        }
        Text(
            text = "Gender",
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    hiddenBottomSheet()
                    onSelectGender(Gender.M)
                }
        ) {
            Text(
                text = "Male",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.h5.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = if (gender == Gender.M) FontWeight.Bold else FontWeight.Normal,
                    color = if (gender == Gender.M) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Center)
            )
        }
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    hiddenBottomSheet()
                    onSelectGender(Gender.F)
                }
        ) {
            Text(
                text = "Female",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.h5.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = if (gender == Gender.F) FontWeight.Bold else FontWeight.Normal,
                    color = if (gender == Gender.F) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Center)
            )
        }
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f))
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp)
                .clickable { hiddenBottomSheet() }
        ) {
            Text(
                text = "Voltar",
                style = MaterialTheme.typography.h5.copy(
                    color = MaterialTheme.colors.primary.copy(
                        alpha = ContentAlpha.medium
                    ),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.profile_pic),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colors.primary,
                        shape = CircleShape
                    )
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Column(Modifier.padding(start = 8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Location",
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 0.sp,
                            baselineShift = BaselineShift.None
                        ),
                    )
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier.size(14.dp)
                    )
                }
                Text(
                    text = "Chicago, US",
                    style = MaterialTheme.typography.h6.copy(
                        color = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.medium),
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }
        }

        HeaderAction(onClick = {}, badge = false, type = HeaderActionType.SEARCH, Modifier.padding(end = 10.dp))
        HeaderAction(onClick = {}, badge = true, type = HeaderActionType.NOTIFICATIONS)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun HeaderAction(
    onClick: () -> Unit,
    badge: Boolean,
    type: HeaderActionType,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .clip(MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f))
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = when (type) {
                HeaderActionType.NOTIFICATIONS -> Icons.Rounded.Notifications
                HeaderActionType.SEARCH -> Icons.Rounded.Search
            },
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .size(18.dp)
                .align(Alignment.TopEnd)
        )

        AnimatedVisibility(
            visible = badge,
            modifier = Modifier
                .padding(2.dp)
                .align(Alignment.TopEnd)
        ) {
            val badgeColor = MaterialTheme.colors.primary
            Canvas(
                modifier = Modifier.size(6.dp),
            ) {
                drawCircle(color = badgeColor)
            }
        }
    }
}

enum class HeaderActionType {
    SEARCH, NOTIFICATIONS
}

@Composable
private fun Banner(
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        color = if (MaterialTheme.colors.isLight) BlueGrey100 else MaterialTheme.colors.surface,
        modifier = modifier
    ) {
        Box() {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Join Our Animal\nLovers Community",
                    style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.high),
                        fontWeight = FontWeight.Bold
                    )
                )
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = DeepPurple200
                    ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = "Join Now",
                        style = MaterialTheme.typography.button.copy(
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Center
                        ),
                    )
                }
            }
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.dog),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(100.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun Filter(
    openGenderSelect: () -> Unit,
    category: Category?,
    gender: Gender?,
    onSelectCategory: (Category?) -> Unit,
    clearFilter: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(24.dp),
        ) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            AnimatedVisibility(visible = gender != null || category != null) {
                Image(
                    imageVector = Icons.Rounded.Cancel,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable(onClick = clearFilter)
                )
            }
        }

        Row() {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(44.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable(onClick = openGenderSelect)
                    .background(if (MaterialTheme.colors.isLight) BlueGrey100 else MaterialTheme.colors.surface)
            ) {
                Icon(
                    imageVector = Icons.Rounded.FilterAlt,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.align(Alignment.Center)
                )

                androidx.compose.animation.AnimatedVisibility(
                    visible = gender != null,
                    modifier = Modifier
                        .padding(2.dp)
                        .align(Alignment.TopEnd)
                ) {
                    val badgeColor = MaterialTheme.colors.secondary
                    Canvas(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(6.dp),
                    ) {
                        drawCircle(color = badgeColor)
                    }
                }
            }

            LazyRow() {
                items(Category.values()) { item ->
                    val checked = remember(category) { category == item }
                    PetButton(
                        onClick = {
                            onSelectCategory(if (checked) null else item)
                        },
                        label = item.label,
                        colors = PetButtonDefaults.colors(
                            backgroundColor = MaterialTheme.colors.primary.copy(alpha = if (checked) 1f else 0.1f),
                            borderColor = if (checked) if (MaterialTheme.colors.isLight) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary else Color.Transparent,
                            labelColor = if (checked) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary
                        ),
                        elevation = if (checked) 8.dp else 0.dp,
                        modifier = Modifier.padding(if (checked) 0.dp else 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun PetEntity(
    pet: Pet,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        elevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Row(
            Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable(onClick = onClick)
                .padding(8.dp)
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = pet.profilePic),
                contentDescription = "Pet Picture",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .size(70.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = pet.name,
                    style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                val tintColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)

                PetEntityRow(
                    icon = Icons.Rounded.LocationOn,
                    iconColor = Purple200,
                    label = pet.location.state,
                    labelColor = tintColor,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${if (pet.gender == Gender.M) "Male" else "Female"} | ",
                        style = MaterialTheme.typography.body2.copy(
                            color = tintColor,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                    )
                    Text(
                        text = pet.birthDate.formattedDate(),
                        style = MaterialTheme.typography.body2.copy(
                            color = tintColor,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                    )
                }

//                PetEntityRow(
//                    icon = if(pet.gender == Gender.M) Icons.Rounded.Male else Icons.Rounded.Female,
//                    iconColor =  if(pet.gender == Gender.M) Blue300 else Red200,
//                    label = if(pet.gender == Gender.M) "Male" else "Female",
//                    labelColor = tintColor,
//                )
//
//                PetEntityRow(
//                    icon = Icons.Rounded.Cake,
//                    iconColor = Teal200,
//                    label = pet.birthDate.formattedDate(),
//                    labelColor = tintColor,
//                )
            }
        }
    }
}

@Composable
private fun PetEntityRow(
    icon: ImageVector,
    iconColor: Color,
    label: String,
    labelColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.body2.copy(
                color = labelColor
            ),
            modifier = Modifier.padding(start = 2.dp)
        )
    }
}
