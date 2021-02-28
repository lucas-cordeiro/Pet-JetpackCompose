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
package com.example.androiddevchallenge.app.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.app.ui.components.PetButton
import com.example.androiddevchallenge.app.ui.components.PetButtonDefaults
import dev.chrisbanes.accompanist.insets.systemBarsPadding

@Composable
fun Splash(
    openHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableStateOf(0) }

    DisposableEffect(step) {
        if (step > 2)
            openHome()

        onDispose { }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {

            LottieImage(
                step = step,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp)
            )

            Row(Modifier.align(Alignment.CenterHorizontally)) {
                for (i in 0..2) {
                    StepIndicator(
                        step = i,
                        currentStep = step,
                        onClick = { if (step != i) step = i },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Labels(
                step = step,
                modifier = Modifier.padding(top = 32.dp)
            )

            BottomButtons(
                step = step,
                updateStep = { step = it },
                openHome = openHome,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Composable
fun LottieImage(
    step: Int,
    modifier: Modifier = Modifier
) {

    val crossFadeAnimSpec = tween<Float>(
        durationMillis = 700,
        easing = FastOutSlowInEasing,
    )

    Crossfade(
        targetState = step,
        animationSpec = crossFadeAnimSpec,
        modifier = modifier
    ) {
        val animationSpec = remember(it) {
            LottieAnimationSpec.RawRes(
                when (it) {
                    0 -> R.raw.lottie_dog
                    1 -> R.raw.lottie_cat
                    else -> R.raw.lottie_heart
                }
            )
        }
        LottieAnimation(
            spec = animationSpec,
            modifier = Modifier
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun StepIndicator(
    step: Int,
    currentStep: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val checked = remember(currentStep) { step == currentStep }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(
                color = MaterialTheme.colors.primary.copy(
                    alpha = if (checked) 1f else 0.2f
                ),
            )
            .clickable {
                onClick()
            }
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        AnimatedVisibility(visible = checked) {
            Spacer(
                modifier = Modifier
                    .width(40.dp)
                    .height(10.dp)
            )
        }
    }
}

@Composable
private fun Labels(
    step: Int,
    modifier: Modifier = Modifier
) {
    val title = remember(step) {
        when (step) {
            0 -> "Find the best pet"
            1 -> "Help many other animals"
            else -> "Save a life"
        }
    }

    val description = remember(step) {
        when (step) {
            0 -> "Animal shelters and rescue groups are brimming with happy, healthy pets just waiting for someone to take them home"
            1 -> "Overburdened shelters take in millions of stray, abused and lost animals every year, and by adopting an animal, you’re making room for others"
            else -> "Each year, it's estimated that more than one million adoptable dogs and cats are euthanized in the United States"
        }
    }

    Crossfade(targetState = step, modifier = modifier) {
        Column(Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = description,
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun BottomButtons(
    step: Int,
    updateStep: (Int) -> Unit,
    openHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        AnimatedVisibility(visible = step < 2) {
            PetButton(
                onClick = openHome,
                label = "Skip",
                colors = PetButtonDefaults.colors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    labelColor = MaterialTheme.colors.onSecondary
                )
            )
        }

        PetButton(
            onClick = { updateStep(step + 1) },
            label = "Next"
        )
    }
}
