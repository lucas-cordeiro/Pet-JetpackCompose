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
package com.example.androiddevchallenge.app.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ChatBubble
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.PhoneInTalk
import androidx.compose.material.icons.rounded.VideoCameraFront
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.app.di.helper.getViewModel
import com.example.androiddevchallenge.app.ui.components.PetButton
import com.example.androiddevchallenge.app.ui.theme.Cyan300
import com.example.androiddevchallenge.app.ui.theme.DeepPurple200
import com.example.androiddevchallenge.app.ui.theme.Green700
import com.example.androiddevchallenge.app.ui.theme.Purple200
import com.example.androiddevchallenge.app.ui.theme.Red400
import com.example.androiddevchallenge.app.utils.formattedDate
import com.example.androiddevchallenge.domain.model.Gender
import com.example.androiddevchallenge.domain.model.Pet
import com.example.androiddevchallenge.domain.model.User
import dev.chrisbanes.accompanist.insets.systemBarsPadding

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Detail(
    petId: String?,
    pressOnBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = getViewModel()
) {
    DisposableEffect(petId) {
        if (!petId.isNullOrBlank())
            viewModel.getPet(petId)

        onDispose {}
    }

    Column(
        modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {

        Box(Modifier.fillMaxWidth()) {
            IconButton(
                onClick = pressOnBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Text(
                text = "Pet",
                style = MaterialTheme.typography.h5.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        val pet = viewModel.pet

        AnimatedVisibility(
            visible = pet != null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            LazyColumn(Modifier.padding(top = 16.dp)) {
                item {
                    PetInfo(
                        pet = pet!!,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                item {
                    UserInfo(
                        user = pet!!.user,
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .padding(horizontal = 16.dp)
                    )
                }

                item {
                    Column(
                        Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = "Details",
                            style = MaterialTheme.typography.h5.copy(
                                color = MaterialTheme.colors.onBackground,
                                fontWeight = FontWeight.Bold,
                            )
                        )

                        Text(
                            text = pet?.description ?: "",
                            style = MaterialTheme.typography.body1.copy(
                                color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
                            ),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                item {
                    Box(Modifier.fillMaxWidth().padding(top = 16.dp)) {
                        PetButton(
                            onClick = {},
                            label = "Adoption",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
private fun PetInfo(
    pet: Pet,
    modifier: Modifier
) {
    val fontColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)

    Surface(
        shape = MaterialTheme.shapes.large,
        elevation = 2.dp,
        modifier = modifier,
    ) {
        Column {
            BoxWithConstraints() {
                Image(
                    bitmap = ImageBitmap.imageResource(id = pet.profilePic),
                    contentDescription = "${pet.name} profile Picture",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .size(maxWidth)
                        .clip(MaterialTheme.shapes.large)
                        .background(color = MaterialTheme.colors.secondary)
                )
            }

            Text(
                text = pet.name,
                style = MaterialTheme.typography.h4.copy(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = null,
                    tint = Purple200,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${pet.location.street}, ${pet.location.city} - ${pet.location.state}",
                    style = MaterialTheme.typography.body1.copy(
                        color = fontColor
                    ),
                    modifier = Modifier.padding(start = 2.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "${if (pet.gender == Gender.M) "Male" else "Female"} | ",
                    style = MaterialTheme.typography.body1.copy(
                        color = fontColor,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier
                )
                Text(
                    text = pet.birthDate.formattedDate(),
                    style = MaterialTheme.typography.body1.copy(
                        color = fontColor,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
private fun UserInfo(
    user: User,
    modifier: Modifier,
) {
    val fontColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)

    Surface(
        shape = MaterialTheme.shapes.large,
        elevation = 2.dp,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = user.profilePic),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(DeepPurple200)
            )

            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 7.dp)
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.body1.copy(
                        color = fontColor,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Pet Owner",
                    style = MaterialTheme.typography.body2.copy(
                        color = fontColor,
                        fontWeight = FontWeight.Medium
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = null,
                        tint = Purple200,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = user.location.state,
                        style = MaterialTheme.typography.body2.copy(
                            color = fontColor
                        ),
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }

            Row() {
                Box(
                    modifier = Modifier
                        .shadow(2.dp, MaterialTheme.shapes.medium)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { }
                        .size(30.dp)
                        .background(color = Green700)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PhoneInTalk,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .shadow(2.dp, MaterialTheme.shapes.medium)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { }
                        .size(30.dp)
                        .background(color = Cyan300)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ChatBubble,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                Box(
                    modifier = Modifier
                        .shadow(2.dp, MaterialTheme.shapes.medium)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { }
                        .size(30.dp)
                        .background(color = Red400)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.VideoCameraFront,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}
