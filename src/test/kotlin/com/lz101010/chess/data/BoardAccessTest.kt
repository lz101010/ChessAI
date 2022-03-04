// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private val INITIAL_BOARD = Board()

class BoardAccessTest {

    @Test
    fun initialBoardAccessFileA() {
        assertThat(INITIAL_BOARD[Square.A8]).isEqualTo(PieceType.R.asBlack)
        assertThat(INITIAL_BOARD[Square.A7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Square.A6]).isNull()
        assertThat(INITIAL_BOARD[Square.A5]).isNull()
        assertThat(INITIAL_BOARD[Square.A4]).isNull()
        assertThat(INITIAL_BOARD[Square.A3]).isNull()
        assertThat(INITIAL_BOARD[Square.A2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Square.A1]).isEqualTo(PieceType.R.asWhite)
    }

    @Test
    fun initialBoardAccessFileB() {
        assertThat(INITIAL_BOARD[Square.B8]).isEqualTo(PieceType.N.asBlack)
        assertThat(INITIAL_BOARD[Square.B7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Square.B6]).isNull()
        assertThat(INITIAL_BOARD[Square.B5]).isNull()
        assertThat(INITIAL_BOARD[Square.B4]).isNull()
        assertThat(INITIAL_BOARD[Square.B3]).isNull()
        assertThat(INITIAL_BOARD[Square.B2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Square.B1]).isEqualTo(PieceType.N.asWhite)
    }

    @Test
    fun initialBoardAccessFileC() {
        assertThat(INITIAL_BOARD[Square.C8]).isEqualTo(PieceType.B.asBlack)
        assertThat(INITIAL_BOARD[Square.C7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Square.C6]).isNull()
        assertThat(INITIAL_BOARD[Square.C5]).isNull()
        assertThat(INITIAL_BOARD[Square.C4]).isNull()
        assertThat(INITIAL_BOARD[Square.C3]).isNull()
        assertThat(INITIAL_BOARD[Square.C2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Square.C1]).isEqualTo(PieceType.B.asWhite)
    }

    @Test
    fun initialBoardAccessFileD() {
        assertThat(INITIAL_BOARD[Square.D8]).isEqualTo(PieceType.Q.asBlack)
        assertThat(INITIAL_BOARD[Square.D7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Square.D6]).isNull()
        assertThat(INITIAL_BOARD[Square.D5]).isNull()
        assertThat(INITIAL_BOARD[Square.D4]).isNull()
        assertThat(INITIAL_BOARD[Square.D3]).isNull()
        assertThat(INITIAL_BOARD[Square.D2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Square.D1]).isEqualTo(PieceType.Q.asWhite)
    }

    @Test
    fun initialBoardAccessFileE() {
        assertThat(INITIAL_BOARD[Square.E8]).isEqualTo(PieceType.K.asBlack)
        assertThat(INITIAL_BOARD[Square.E7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Square.E6]).isNull()
        assertThat(INITIAL_BOARD[Square.E5]).isNull()
        assertThat(INITIAL_BOARD[Square.E4]).isNull()
        assertThat(INITIAL_BOARD[Square.E3]).isNull()
        assertThat(INITIAL_BOARD[Square.E2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Square.E1]).isEqualTo(PieceType.K.asWhite)
    }

    @Test
    fun initialBoardAccessFileF() {
        assertThat(INITIAL_BOARD[Square.F8]).isEqualTo(PieceType.B.asBlack)
        assertThat(INITIAL_BOARD[Square.F7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Square.F6]).isNull()
        assertThat(INITIAL_BOARD[Square.F5]).isNull()
        assertThat(INITIAL_BOARD[Square.F4]).isNull()
        assertThat(INITIAL_BOARD[Square.F3]).isNull()
        assertThat(INITIAL_BOARD[Square.F2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Square.F1]).isEqualTo(PieceType.B.asWhite)
    }

    @Test
    fun initialBoardAccessFileG() {
        assertThat(INITIAL_BOARD[Square.G8]).isEqualTo(PieceType.N.asBlack)
        assertThat(INITIAL_BOARD[Square.G7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Square.G6]).isNull()
        assertThat(INITIAL_BOARD[Square.G5]).isNull()
        assertThat(INITIAL_BOARD[Square.G4]).isNull()
        assertThat(INITIAL_BOARD[Square.G3]).isNull()
        assertThat(INITIAL_BOARD[Square.G2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Square.G1]).isEqualTo(PieceType.N.asWhite)
    }

    @Test
    fun initialBoardAccessFileH() {
        assertThat(INITIAL_BOARD[Square.H8]).isEqualTo(PieceType.R.asBlack)
        assertThat(INITIAL_BOARD[Square.H7]).isEqualTo(PieceType.P.asBlack)
        assertThat(INITIAL_BOARD[Square.H6]).isNull()
        assertThat(INITIAL_BOARD[Square.H5]).isNull()
        assertThat(INITIAL_BOARD[Square.H4]).isNull()
        assertThat(INITIAL_BOARD[Square.H3]).isNull()
        assertThat(INITIAL_BOARD[Square.H2]).isEqualTo(PieceType.P.asWhite)
        assertThat(INITIAL_BOARD[Square.H1]).isEqualTo(PieceType.R.asWhite)
    }
}
