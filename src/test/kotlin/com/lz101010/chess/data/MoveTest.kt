// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MoveTest {
    @Test
    fun defaultPrint_passes() {
        assertThat(Move(PieceType.P.asBlack, Square.A1, Square.A2).toString()).isEqualTo("a1a2")
        assertThat(Move(PieceType.R.asBlack, Square.B1, Square.B2).toString()).isEqualTo("Rb1b2")
        assertThat(Move(PieceType.N.asBlack, Square.C1, Square.C2).toString()).isEqualTo("Nc1c2")
        assertThat(Move(PieceType.B.asBlack, Square.D1, Square.D2).toString()).isEqualTo("Bd1d2")
        assertThat(Move(PieceType.Q.asBlack, Square.E1, Square.E2).toString()).isEqualTo("Qe1e2")
        assertThat(Move(PieceType.K.asBlack, Square.F1, Square.F2).toString()).isEqualTo("Kf1f2")

        assertThat(Move(PieceType.P.asWhite, Square.A3, Square.A4).toString()).isEqualTo("a3a4")
        assertThat(Move(PieceType.R.asWhite, Square.B3, Square.B4).toString()).isEqualTo("Rb3b4")
        assertThat(Move(PieceType.N.asWhite, Square.C3, Square.C4).toString()).isEqualTo("Nc3c4")
        assertThat(Move(PieceType.B.asWhite, Square.D3, Square.D4).toString()).isEqualTo("Bd3d4")
        assertThat(Move(PieceType.Q.asWhite, Square.E3, Square.E4).toString()).isEqualTo("Qe3e4")
        assertThat(Move(PieceType.K.asWhite, Square.F3, Square.F4).toString()).isEqualTo("Kf3f4")
    }

    @Test
    fun prettyPrint_passes() {
        assertThat(Move(PieceType.P.asBlack, Square.A1, Square.A2).pretty).isEqualTo("♟a1a2")
        assertThat(Move(PieceType.R.asBlack, Square.B1, Square.B2).pretty).isEqualTo("♜b1b2")
        assertThat(Move(PieceType.N.asBlack, Square.C1, Square.C2).pretty).isEqualTo("♞c1c2")
        assertThat(Move(PieceType.B.asBlack, Square.D1, Square.D2).pretty).isEqualTo("♝d1d2")
        assertThat(Move(PieceType.Q.asBlack, Square.E1, Square.E2).pretty).isEqualTo("♛e1e2")
        assertThat(Move(PieceType.K.asBlack, Square.F1, Square.F2).pretty).isEqualTo("♚f1f2")

        assertThat(Move(PieceType.P.asWhite, Square.A3, Square.A4).pretty).isEqualTo("♙a3a4")
        assertThat(Move(PieceType.R.asWhite, Square.B3, Square.B4).pretty).isEqualTo("♖b3b4")
        assertThat(Move(PieceType.N.asWhite, Square.C3, Square.C4).pretty).isEqualTo("♘c3c4")
        assertThat(Move(PieceType.B.asWhite, Square.D3, Square.D4).pretty).isEqualTo("♗d3d4")
        assertThat(Move(PieceType.Q.asWhite, Square.E3, Square.E4).pretty).isEqualTo("♕e3e4")
        assertThat(Move(PieceType.K.asWhite, Square.F3, Square.F4).pretty).isEqualTo("♔f3f4")
    }

    @Test
    fun promotionPrint_passes() {
        assertThat(Move(PieceType.P.asBlack, Square.A1, Square.A2, PieceType.Q).toString()).isEqualTo("a1a2Q")
    }
}
