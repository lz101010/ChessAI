// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MoveTest {
    @Test
    fun printing_passes() {
        assertThat(Move(PieceType.P.asBlack, Field.A1, Field.A2).toString()).isEqualTo("a1a2")
        assertThat(Move(PieceType.Q.asWhite, Field.B7, Field.G3).pretty).isEqualTo("♕b7g3")
    }
}
