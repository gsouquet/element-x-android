/*
 * Copyright (c) 2023 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.element.android.features.rageshake.preferences

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

open class RageshakePreferencesStateProvider : PreviewParameterProvider<RageshakePreferencesState> {
    override val values: Sequence<RageshakePreferencesState>
        get() = sequenceOf(
            aRageshakePreferencesState().copy(isEnabled = true, isSupported = true, sensitivity = 0.5f),
            aRageshakePreferencesState().copy(isEnabled = true, isSupported = false, sensitivity = 0.5f),
        )
}

fun aRageshakePreferencesState() = RageshakePreferencesState(
    isEnabled = false,
    isSupported = true,
    sensitivity = 0.3f,
    eventSink = {}
)
