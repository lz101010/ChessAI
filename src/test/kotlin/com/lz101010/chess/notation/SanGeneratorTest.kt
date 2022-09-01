// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.notation

import com.lz101010.chess.data.Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SanGeneratorTest {
    @Test
    fun defaultBoardSan_passes() {
        assertThat(SanGenerator.generate(Game(listOf()))).isBlank
    }
}
