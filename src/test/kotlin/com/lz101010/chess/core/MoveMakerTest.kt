// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MoveMakerTest {
    @Test
    fun movePe2e4_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, Move(PieceType.P.asWhite, from = Square.E2, to = Square.E4))

        assertThat(boardAfterE4[Square.E4]).isEqualTo(PieceType.P.asWhite)
        assertThat(boardAfterE4[Square.E2]).isNull()

        for (square in Square.values().filterNot { it == Square.E2 || it == Square.E4 }) {
            val defaultPiece = defaultBoard[square]
            if (defaultPiece == null) {
                assertThat(boardAfterE4[square]).isNull()
            } else {
                assertThat(boardAfterE4[square]).isEqualTo(defaultPiece)
            }
        }
    }

    @Test
    fun moveBf1e2_fails() {
        val defaultBoard = Board()

        assertThrows<IllegalArgumentException> {
            MoveMaker.move(defaultBoard, Move(PieceType.B.asWhite, from = Square.F1, to = Square.E2))
        }.let {
            assertThat(it.message).isEqualTo("${Square.E2} is occupied by same color piece")
        }
    }

    @Test
    fun moveNf1e3_fails() {
        val defaultBoard = Board()

        assertThrows<IllegalArgumentException> {
            MoveMaker.move(defaultBoard, Move(PieceType.N.asWhite, from = Square.F1, to = Square.E3))
        }.let {
            assertThat(it.message).isEqualTo("${PieceType.B.asWhite} != ${PieceType.N.asWhite}")
        }
    }

    @Test
    fun moveNg8f6_fails() {
        val defaultBoard = Board()

        assertThrows<IllegalArgumentException> {
            MoveMaker.move(defaultBoard, Move(PieceType.N.asBlack, from = Square.G8, to = Square.F6))
        }.let {
            assertThat(it.message).isEqualTo("white to move")
        }
    }

    @Test
    fun movePe4Pe5Ke2_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, Move(PieceType.P.asWhite, from = Square.E2, to = Square.E4))
        val boardAfterE5 = MoveMaker.move(boardAfterE4, Move(PieceType.P.asBlack, from = Square.E7, to = Square.E5))
        val boardAfterE2 = MoveMaker.move(boardAfterE5, Move(PieceType.K.asWhite, from = Square.E1, to = Square.E2))

        assertThat(boardAfterE2.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePh4Pe5Rh2_passes() {
        val defaultBoard = Board()
        val boardAfterH4 = MoveMaker.move(defaultBoard, Move(PieceType.P.asWhite, from = Square.H2, to = Square.H4))
        val boardAfterE5 = MoveMaker.move(boardAfterH4, Move(PieceType.P.asBlack, from = Square.E7, to = Square.E5))
        val boardAfterH2 = MoveMaker.move(boardAfterE5, Move(PieceType.R.asWhite, from = Square.H1, to = Square.H2))

        assertThat(boardAfterH2.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_Q, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePa4Pe5Ra2_passes() {
        val defaultBoard = Board()
        val boardAfterA4 = MoveMaker.move(defaultBoard, Move(PieceType.P.asWhite, from = Square.A2, to = Square.A4))
        val boardAfterE5 = MoveMaker.move(boardAfterA4, Move(PieceType.P.asBlack, from = Square.E7, to = Square.E5))
        val boardAfterA2 = MoveMaker.move(boardAfterE5, Move(PieceType.R.asWhite, from = Square.A1, to = Square.A2))

        assertThat(boardAfterA2.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePe4Pe5Pd4Ke7_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, Move(PieceType.P.asWhite, from = Square.E2, to = Square.E4))
        val boardAfterE5 = MoveMaker.move(boardAfterE4, Move(PieceType.P.asBlack, from = Square.E7, to = Square.E5))
        val boardAfterD4 = MoveMaker.move(boardAfterE5, Move(PieceType.P.asWhite, from = Square.D2, to = Square.D4))
        val boardAfterE7 = MoveMaker.move(boardAfterD4, Move(PieceType.K.asBlack, from = Square.E8, to = Square.E7))

        assertThat(boardAfterE7.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.WHITE_Q)
    }

    @Test
    fun movePe4Ph5Pd4Rh7_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, Move(PieceType.P.asWhite, from = Square.E2, to = Square.E4))
        val boardAfterH5 = MoveMaker.move(boardAfterE4, Move(PieceType.P.asBlack, from = Square.H7, to = Square.H5))
        val boardAfterD4 = MoveMaker.move(boardAfterH5, Move(PieceType.P.asWhite, from = Square.D2, to = Square.D4))
        val boardAfterH7 = MoveMaker.move(boardAfterD4, Move(PieceType.R.asBlack, from = Square.H8, to = Square.H7))

        assertThat(boardAfterH7.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.WHITE_Q, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePe4Pa5Pd4Ra7_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, Move(PieceType.P.asWhite, from = Square.E2, to = Square.E4))
        val boardAfterA5 = MoveMaker.move(boardAfterE4, Move(PieceType.P.asBlack, from = Square.A7, to = Square.A5))
        val boardAfterD4 = MoveMaker.move(boardAfterA5, Move(PieceType.P.asWhite, from = Square.D2, to = Square.D4))
        val boardAfterA7 = MoveMaker.move(boardAfterD4, Move(PieceType.R.asBlack, from = Square.A8, to = Square.A7))

        assertThat(boardAfterA7.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.WHITE_Q, CastlingOption.BLACK_K)
    }

    @Test
    fun movePe4Pe5Ke2Ke7_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, Move(PieceType.P.asWhite, from = Square.E2, to = Square.E4))
        val boardAfterE5 = MoveMaker.move(boardAfterE4, Move(PieceType.P.asBlack, from = Square.E7, to = Square.E5))
        val boardAfterE2 = MoveMaker.move(boardAfterE5, Move(PieceType.K.asWhite, from = Square.E1, to = Square.E2))
        val boardAfterE7 = MoveMaker.move(boardAfterE2, Move(PieceType.K.asBlack, from = Square.E8, to = Square.E7))

        assertThat(boardAfterE7.castlingOptions).isEmpty()
    }
}
