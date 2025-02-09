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

@file:OptIn(ExperimentalCoroutinesApi::class)

package io.element.android.features.preferences.impl.developer

import app.cash.molecule.RecompositionClock
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.element.android.libraries.featureflag.api.FeatureFlags
import io.element.android.libraries.featureflag.test.FakeFeatureFlagService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeveloperSettingsPresenterTest {
    @Test
    fun `present - ensures initial state is correct`() = runTest {
        val presenter = DeveloperSettingsPresenter(
            FakeFeatureFlagService()
        )
        moleculeFlow(RecompositionClock.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            assertThat(initialState.features).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `present - ensures feature list is loaded`() = runTest {
        val presenter = DeveloperSettingsPresenter(
            FakeFeatureFlagService()
        )
        moleculeFlow(RecompositionClock.Immediate) {
            presenter.present()
        }.test {
            skipItems(1)
            val state = awaitItem()
            assertThat(state.features).hasSize(FeatureFlags.values().size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `present - ensures state is updated when enabled feature event is triggered`() = runTest {
        val presenter = DeveloperSettingsPresenter(
            FakeFeatureFlagService()
        )
        moleculeFlow(RecompositionClock.Immediate) {
            presenter.present()
        }.test {
            skipItems(1)
            val stateBeforeEvent = awaitItem()
            val featureBeforeEvent = stateBeforeEvent.features.first()
            stateBeforeEvent.eventSink(DeveloperSettingsEvents.UpdateEnabledFeature(featureBeforeEvent, !featureBeforeEvent.isEnabled))
            val stateAfterEvent = awaitItem()
            val featureAfterEvent = stateAfterEvent.features.first()
            assertThat(featureBeforeEvent.key).isEqualTo(featureAfterEvent.key)
            assertThat(featureBeforeEvent.isEnabled).isNotEqualTo(featureAfterEvent.isEnabled)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
