// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MoveTest {
    @Test
    fun defaultPrint_passes() {
        assertThat(Move(Square.A1, Square.A2).toString()).isEqualTo("a1a2")
        assertThat(Move(Square.B1, Square.B2).toString()).isEqualTo("b1b2")
        assertThat(Move(Square.C1, Square.C2).toString()).isEqualTo("c1c2")
        assertThat(Move(Square.D1, Square.D2).toString()).isEqualTo("d1d2")
        assertThat(Move(Square.E1, Square.E2).toString()).isEqualTo("e1e2")
        assertThat(Move(Square.F1, Square.F2).toString()).isEqualTo("f1f2")

        assertThat(Move(Square.A3, Square.A4).toString()).isEqualTo("a3a4")
        assertThat(Move(Square.B3, Square.B4).toString()).isEqualTo("b3b4")
        assertThat(Move(Square.C3, Square.C4).toString()).isEqualTo("c3c4")
        assertThat(Move(Square.D3, Square.D4).toString()).isEqualTo("d3d4")
        assertThat(Move(Square.E3, Square.E4).toString()).isEqualTo("e3e4")
        assertThat(Move(Square.F3, Square.F4).toString()).isEqualTo("f3f4")
    }

    @Test
    fun promotionPrint_passes() {
        assertThat(Move(Square.A1, Square.A2, PieceType.Q).toString()).isEqualTo("a1a2Q")
    }
}
