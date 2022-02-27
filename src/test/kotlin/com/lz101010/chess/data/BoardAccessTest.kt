// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private val INITIAL_BOARD = Board()

class BoardAccessTest {

    @Test
    fun initialBoardAccessColA() {
        assertThat(INITIAL_BOARD[Field.A8]).isEqualTo(PieceType.R.asBlack)
        assertThat(INITIAL_BOARD[Field.A7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Field.A6]).isNull()
        assertThat(INITIAL_BOARD[Field.A5]).isNull()
        assertThat(INITIAL_BOARD[Field.A4]).isNull()
        assertThat(INITIAL_BOARD[Field.A3]).isNull()
        assertThat(INITIAL_BOARD[Field.A2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Field.A1]).isEqualTo(PieceType.R.asWhite)
    }

    @Test
    fun initialBoardAccessColB() {
        assertThat(INITIAL_BOARD[Field.B8]).isEqualTo(PieceType.N.asBlack)
        assertThat(INITIAL_BOARD[Field.B7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Field.B6]).isNull()
        assertThat(INITIAL_BOARD[Field.B5]).isNull()
        assertThat(INITIAL_BOARD[Field.B4]).isNull()
        assertThat(INITIAL_BOARD[Field.B3]).isNull()
        assertThat(INITIAL_BOARD[Field.B2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Field.B1]).isEqualTo(PieceType.N.asWhite)
    }

    @Test
    fun initialBoardAccessColC() {
        assertThat(INITIAL_BOARD[Field.C8]).isEqualTo(PieceType.B.asBlack)
        assertThat(INITIAL_BOARD[Field.C7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Field.C6]).isNull()
        assertThat(INITIAL_BOARD[Field.C5]).isNull()
        assertThat(INITIAL_BOARD[Field.C4]).isNull()
        assertThat(INITIAL_BOARD[Field.C3]).isNull()
        assertThat(INITIAL_BOARD[Field.C2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Field.C1]).isEqualTo(PieceType.B.asWhite)
    }

    @Test
    fun initialBoardAccessColD() {
        assertThat(INITIAL_BOARD[Field.D8]).isEqualTo(PieceType.Q.asBlack)
        assertThat(INITIAL_BOARD[Field.D7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Field.D6]).isNull()
        assertThat(INITIAL_BOARD[Field.D5]).isNull()
        assertThat(INITIAL_BOARD[Field.D4]).isNull()
        assertThat(INITIAL_BOARD[Field.D3]).isNull()
        assertThat(INITIAL_BOARD[Field.D2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Field.D1]).isEqualTo(PieceType.Q.asWhite)
    }

    @Test
    fun initialBoardAccessColE() {
        assertThat(INITIAL_BOARD[Field.E8]).isEqualTo(PieceType.K.asBlack)
        assertThat(INITIAL_BOARD[Field.E7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Field.E6]).isNull()
        assertThat(INITIAL_BOARD[Field.E5]).isNull()
        assertThat(INITIAL_BOARD[Field.E4]).isNull()
        assertThat(INITIAL_BOARD[Field.E3]).isNull()
        assertThat(INITIAL_BOARD[Field.E2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Field.E1]).isEqualTo(PieceType.K.asWhite)
    }

    @Test
    fun initialBoardAccessColF() {
        assertThat(INITIAL_BOARD[Field.F8]).isEqualTo(PieceType.B.asBlack)
        assertThat(INITIAL_BOARD[Field.F7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Field.F6]).isNull()
        assertThat(INITIAL_BOARD[Field.F5]).isNull()
        assertThat(INITIAL_BOARD[Field.F4]).isNull()
        assertThat(INITIAL_BOARD[Field.F3]).isNull()
        assertThat(INITIAL_BOARD[Field.F2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Field.F1]).isEqualTo(PieceType.B.asWhite)
    }

    @Test
    fun initialBoardAccessColG() {
        assertThat(INITIAL_BOARD[Field.G8]).isEqualTo(PieceType.N.asBlack)
        assertThat(INITIAL_BOARD[Field.G7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Field.G6]).isNull()
        assertThat(INITIAL_BOARD[Field.G5]).isNull()
        assertThat(INITIAL_BOARD[Field.G4]).isNull()
        assertThat(INITIAL_BOARD[Field.G3]).isNull()
        assertThat(INITIAL_BOARD[Field.G2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Field.G1]).isEqualTo(PieceType.N.asWhite)
    }

    @Test
    fun initialBoardAccessColH() {
        assertThat(INITIAL_BOARD[Field.H8]).isEqualTo(PieceType.R.asBlack)
        assertThat(INITIAL_BOARD[Field.H7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Field.H6]).isNull()
        assertThat(INITIAL_BOARD[Field.H5]).isNull()
        assertThat(INITIAL_BOARD[Field.H4]).isNull()
        assertThat(INITIAL_BOARD[Field.H3]).isNull()
        assertThat(INITIAL_BOARD[Field.H2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Field.H1]).isEqualTo(PieceType.R.asWhite)
    }
}
