// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.*
import com.lz101010.chess.support.OpeningMoves
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MoveMakerTest {
    @Test
    fun movePe2e4_passes() {
        val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)

        assertThat(boardAfterE4[Square.E4]).isEqualTo(PieceType.P.asWhite)
        assertThat(boardAfterE4[Square.E2]).isNull()

        for (square in Square.values().filterNot { it == Square.E2 || it == Square.E4 }) {
            val defaultPiece = Board.default[square]
            if (defaultPiece == null) {
                assertThat(boardAfterE4[square]).isNull()
            } else {
                assertThat(boardAfterE4[square]).isEqualTo(defaultPiece)
            }
        }
    }

    @Test
    fun moveBf1e2_fails() {
        assertThrows<IllegalArgumentException> {
            MoveMaker.move(Board.default, Move(PieceType.B.asWhite, from = Square.F1, to = Square.E2))
        }.let {
            assertThat(it.message).isEqualTo("${Square.E2} is occupied by same color piece")
        }
    }

    @Test
    fun moveNf1e3_fails() {
        assertThrows<IllegalArgumentException> {
            MoveMaker.move(Board.default, Move(PieceType.N.asWhite, from = Square.F1, to = Square.E3))
        }.let {
            assertThat(it.message).isEqualTo("${PieceType.B.asWhite} != ${PieceType.N.asWhite}")
        }
    }

    @Test
    fun moveNg8f6_fails() {
        assertThrows<IllegalArgumentException> {
            MoveMaker.move(Board.default, Move(PieceType.N.asBlack, from = Square.G8, to = Square.F6))
        }.let {
            assertThat(it.message).isEqualTo("white to move")
        }
    }

    @Test
    fun moveE4Nf6_fails() {
        assertThrows<IllegalArgumentException> {
            val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)
            MoveMaker.move(boardAfterE4, OpeningMoves.D4)
        }.let {
            assertThat(it.message).isEqualTo("black to move")
        }
    }

    @Test
    fun movePe4Pe5Ke2_passes() {
        val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)
        val boardAfterE5 = MoveMaker.move(boardAfterE4, OpeningMoves.E5)
        val boardAfterE2 = MoveMaker.move(boardAfterE5, Move(PieceType.K.asWhite, from = Square.E1, to = Square.E2))

        assertThat(boardAfterE2.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePh4Pe5Rh2_passes() {
        val boardAfterH4 = MoveMaker.move(Board.default, OpeningMoves.H4)
        val boardAfterE5 = MoveMaker.move(boardAfterH4, OpeningMoves.E5)
        val boardAfterH2 = MoveMaker.move(boardAfterE5, Move(PieceType.R.asWhite, from = Square.H1, to = Square.H2))

        assertThat(boardAfterH2.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_Q, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePa4Pe5Ra2_passes() {
        val boardAfterA4 = MoveMaker.move(Board.default, OpeningMoves.A4)
        val boardAfterE5 = MoveMaker.move(boardAfterA4, OpeningMoves.E5)
        val boardAfterA2 = MoveMaker.move(boardAfterE5, Move(PieceType.R.asWhite, from = Square.A1, to = Square.A2))

        assertThat(boardAfterA2.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun moveRookFromBFile_passes() {
        val boardAfterA4 = MoveMaker.move(Board.default, OpeningMoves.A4)
        val boardAfterE5 = MoveMaker.move(boardAfterA4, OpeningMoves.E5)
        val boardAfterA2 = MoveMaker.move(boardAfterE5, Move(PieceType.R.asWhite, from = Square.A1, to = Square.A3))
        val boardAfterD5 = MoveMaker.move(boardAfterA2, OpeningMoves.D5)
        val boardAfterB3 = MoveMaker.move(boardAfterD5, Move(PieceType.R.asWhite, from = Square.A3, to = Square.B3))
        val boardAfterF5 = MoveMaker.move(boardAfterB3, OpeningMoves.F5)
        val boardAfterH3 = MoveMaker.move(boardAfterF5, Move(PieceType.R.asWhite, from = Square.B3, to = Square.H3))

        assertThat(boardAfterH3.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePe4Pe5Pd4Ke7_passes() {
        val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)
        val boardAfterE5 = MoveMaker.move(boardAfterE4, OpeningMoves.E5)
        val boardAfterD4 = MoveMaker.move(boardAfterE5, OpeningMoves.D4)
        val boardAfterE7 = MoveMaker.move(boardAfterD4, Move(PieceType.K.asBlack, from = Square.E8, to = Square.E7))

        assertThat(boardAfterE7.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.WHITE_Q)
    }

    @Test
    fun movePe4Ph5Pd4Rh7_passes() {
        val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)
        val boardAfterH5 = MoveMaker.move(boardAfterE4, OpeningMoves.H5)
        val boardAfterD4 = MoveMaker.move(boardAfterH5, OpeningMoves.D4)
        val boardAfterH7 = MoveMaker.move(boardAfterD4, Move(PieceType.R.asBlack, from = Square.H8, to = Square.H7))

        assertThat(boardAfterH7.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.WHITE_Q, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePe4Pa5Pd4Ra7_passes() {
        val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)
        val boardAfterA5 = MoveMaker.move(boardAfterE4, OpeningMoves.A5)
        val boardAfterD4 = MoveMaker.move(boardAfterA5, OpeningMoves.D4)
        val boardAfterA7 = MoveMaker.move(boardAfterD4, Move(PieceType.R.asBlack, from = Square.A8, to = Square.A7))

        assertThat(boardAfterA7.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.WHITE_Q, CastlingOption.BLACK_K)
    }

    @Test
    fun movePe4Pe5Ke2Ke7_passes() {
        val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)
        val boardAfterE5 = MoveMaker.move(boardAfterE4, OpeningMoves.E5)
        val boardAfterE2 = MoveMaker.move(boardAfterE5, Move(PieceType.K.asWhite, from = Square.E1, to = Square.E2))
        val boardAfterE7 = MoveMaker.move(boardAfterE2, Move(PieceType.K.asBlack, from = Square.E8, to = Square.E7))

        assertThat(boardAfterE7.castlingOptions).isEmpty()
    }

    @Test
    fun enPassantA4A5_passes() {
        val boardAfterA4 = MoveMaker.move(Board.default, OpeningMoves.A4)
        assertThat(boardAfterA4.enPassant).isEqualTo(EnPassantOption.A3)

        val boardAfterA5 = MoveMaker.move(boardAfterA4, OpeningMoves.A5)
        assertThat(boardAfterA5.enPassant).isEqualTo(EnPassantOption.A6)
    }

    @Test
    fun enPassantB4B5_passes() {
        val boardAfterB4 = MoveMaker.move(Board.default, OpeningMoves.B4)
        assertThat(boardAfterB4.enPassant).isEqualTo(EnPassantOption.B3)

        val boardAfterB5 = MoveMaker.move(boardAfterB4, OpeningMoves.B5)
        assertThat(boardAfterB5.enPassant).isEqualTo(EnPassantOption.B6)
    }

    @Test
    fun enPassantC4C5_passes() {
        val boardAfterC4 = MoveMaker.move(Board.default, OpeningMoves.C4)
        assertThat(boardAfterC4.enPassant).isEqualTo(EnPassantOption.C3)

        val boardAfterC5 = MoveMaker.move(boardAfterC4, OpeningMoves.C5)
        assertThat(boardAfterC5.enPassant).isEqualTo(EnPassantOption.C6)
    }

    @Test
    fun enPassantD4D5_passes() {
        val boardAfterD4 = MoveMaker.move(Board.default, OpeningMoves.D4)
        assertThat(boardAfterD4.enPassant).isEqualTo(EnPassantOption.D3)

        val boardAfterD5 = MoveMaker.move(boardAfterD4, OpeningMoves.D5)
        assertThat(boardAfterD5.enPassant).isEqualTo(EnPassantOption.D6)
    }

    @Test
    fun enPassantE4E5_passes() {
        val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)
        assertThat(boardAfterE4.enPassant).isEqualTo(EnPassantOption.E3)

        val boardAfterE5 = MoveMaker.move(boardAfterE4, OpeningMoves.E5)
        assertThat(boardAfterE5.enPassant).isEqualTo(EnPassantOption.E6)
    }

    @Test
    fun enPassantF4F5_passes() {
        val boardAfterF4 = MoveMaker.move(Board.default, OpeningMoves.F4)
        assertThat(boardAfterF4.enPassant).isEqualTo(EnPassantOption.F3)

        val boardAfterF5 = MoveMaker.move(boardAfterF4, OpeningMoves.F5)
        assertThat(boardAfterF5.enPassant).isEqualTo(EnPassantOption.F6)
    }

    @Test
    fun enPassantG4G5_passes() {
        val boardAfterG4 = MoveMaker.move(Board.default, OpeningMoves.G4)
        assertThat(boardAfterG4.enPassant).isEqualTo(EnPassantOption.G3)

        val boardAfterG5 = MoveMaker.move(boardAfterG4, OpeningMoves.G5)
        assertThat(boardAfterG5.enPassant).isEqualTo(EnPassantOption.G6)
    }

    @Test
    fun enPassantH4H5_passes() {
        val boardAfterH4 = MoveMaker.move(Board.default, OpeningMoves.H4)
        assertThat(boardAfterH4.enPassant).isEqualTo(EnPassantOption.H3)

        val boardAfterH5 = MoveMaker.move(boardAfterH4, OpeningMoves.H5)
        assertThat(boardAfterH5.enPassant).isEqualTo(EnPassantOption.H6)
    }
}
