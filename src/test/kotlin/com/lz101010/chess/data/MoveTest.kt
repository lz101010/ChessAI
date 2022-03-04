// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MoveTest {
    @Test
    fun printing_passes() {
        assertThat(Move(PieceType.P.asBlack, Square.A1, Square.A2).toString()).isEqualTo("a1a2")
        assertThat(Move(PieceType.Q.asWhite, Square.B7, Square.G3).pretty).isEqualTo("♕b7g3")
    }
}
