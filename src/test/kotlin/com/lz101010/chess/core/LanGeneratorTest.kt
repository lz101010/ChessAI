// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.Game
import com.lz101010.chess.data.Move
import com.lz101010.chess.support.OpeningMoves
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LanGeneratorTest {
    @Test
    fun defaultBoardSan_passes() {
        assertThat(LanGenerator.generate(after())).isBlank
    }

    @Test
    fun boardOpeningSan_passes() {
        assertThat(LanGenerator.generate(after(OpeningMoves.E4)))
            .isEqualTo("1. ♙e2e4 *")
        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.E5)))
            .isEqualTo("1. ♙e2e4 ♟e7e5")

        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.E5, OpeningMoves.Nf3)))
            .isEqualTo("""
                1. ♙e2e4 ♟e7e5
                2. ♘g1f3 *
            """.trimIndent())
        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.E5, OpeningMoves.Nf3, OpeningMoves.Nc6)))
            .isEqualTo("""
                1. ♙e2e4 ♟e7e5
                2. ♘g1f3 ♞b8c6
            """.trimIndent())
    }

    private fun after(vararg moves: Move): Game {
        return Game(moves.toList())
    }
}
