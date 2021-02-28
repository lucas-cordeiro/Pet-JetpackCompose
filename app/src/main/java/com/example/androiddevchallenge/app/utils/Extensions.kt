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
package com.example.androiddevchallenge.app.utils

import java.util.Calendar
import java.util.Date

fun Long.formattedDate(): String {
    val now = System.currentTimeMillis()

    val d1 = Date(this * 1000)
    val d2 = Date(now)

    val calendar: Calendar = Calendar.getInstance()

    calendar.time = d1
    val month1: Int = 12 * calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH)

    calendar.time = d2
    val month2: Int = 12 * calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH)

    val monthCount = Math.abs(month2 - month1)

    if (monthCount <= 1) return "1 month"
    if (monthCount < 12) return "$monthCount months"

    val yearCount = (monthCount / 12)
    if (yearCount <= 1) return "1 year"
    return "$yearCount years"
}
