/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.customization.picker.settings.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.customization.picker.settings.domain.interactor.ColorContrastSectionInteractor
import com.android.themepicker.R
import com.android.wallpaper.picker.common.icon.ui.viewmodel.Icon
import com.android.wallpaper.picker.common.text.ui.viewmodel.Text
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ColorContrastSectionViewModel
private constructor(
    colorContrastSectionInteractor: ColorContrastSectionInteractor,
) : ViewModel() {

    val summary: Flow<ColorContrastSectionDataViewModel> =
        colorContrastSectionInteractor.contrast.map { contrastValue ->
            when (contrastValue) {
                ContrastValue.STANDARD.value ->
                    ColorContrastSectionDataViewModel(
                        Text.Resource(R.string.color_contrast_default_title),
                        Icon.Resource(
                            res = R.drawable.ic_contrast_standard,
                            contentDescription = null,
                        )
                    )
                ContrastValue.MEDIUM.value ->
                    ColorContrastSectionDataViewModel(
                        Text.Resource(R.string.color_contrast_medium_title),
                        Icon.Resource(
                            res = R.drawable.ic_contrast_medium,
                            contentDescription = null,
                        )
                    )
                ContrastValue.HIGH.value ->
                    ColorContrastSectionDataViewModel(
                        Text.Resource(R.string.color_contrast_high_title),
                        Icon.Resource(
                            res = R.drawable.ic_contrast_high,
                            contentDescription = null,
                        )
                    )
                else -> {
                    println("Invalid contrast value: $contrastValue")
                    throw IllegalArgumentException("Invalid contrast value")
                }
            }
        }

    enum class ContrastValue(val value: Float) {
        STANDARD(0f),
        MEDIUM(0.5f),
        HIGH(1f)
    }

    @Singleton
    class Factory
    @Inject
    constructor(
        private val colorContrastSectionInteractor: ColorContrastSectionInteractor,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ColorContrastSectionViewModel(
                colorContrastSectionInteractor = colorContrastSectionInteractor,
            )
                as T
        }
    }
}