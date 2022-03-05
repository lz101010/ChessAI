// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.*
import com.lz101010.chess.support.OpeningMoves
import com.lz101010.chess.support.move
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MoveMakerTest {
    @Test
    fun movePe2e4_passes() {
        val boardAfterE4 = Board.default.move(OpeningMoves.E4)

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
            Board.default.move(Move(PieceType.B.asWhite, from = Square.F1, to = Square.E2))
        }.let {
            assertThat(it.message).isEqualTo("${Square.E2} is occupied by same color piece")
        }
    }

    @Test
    fun moveNf1e3_fails() {
        assertThrows<IllegalArgumentException> {
            Board.default.move(Move(PieceType.N.asWhite, from = Square.F1, to = Square.E3))
        }.let {
            assertThat(it.message).isEqualTo("${PieceType.B.asWhite} != ${PieceType.N.asWhite}")
        }
    }

    @Test
    fun moveNg8f6_fails() {
        assertThrows<IllegalArgumentException> {
            Board.default.move(Move(PieceType.N.asBlack, from = Square.G8, to = Square.F6))
        }.let {
            assertThat(it.message).isEqualTo("white to move")
        }
    }

    @Test
    fun moveE4Nf6_fails() {
        assertThrows<IllegalArgumentException> {
            Board.default
                .move(OpeningMoves.E4)
                .move(OpeningMoves.D4)
        }.let {
            assertThat(it.message).isEqualTo("black to move")
        }
    }

    @Test
    fun movePe4Pe5Ke2_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.E5)
            .move(Move(PieceType.K.asWhite, from = Square.E1, to = Square.E2))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePh4Pe5Rh2_passes() {
        val board = Board.default
            .move(OpeningMoves.H4)
            .move(OpeningMoves.E5)
            .move(Move(PieceType.R.asWhite, from = Square.H1, to = Square.H2))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_Q, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePa4Pe5Ra2_passes() {
        val board = Board.default
            .move(OpeningMoves.A4)
            .move(OpeningMoves.E5)
            .move(Move(PieceType.R.asWhite, from = Square.A1, to = Square.A2))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun moveRookFromBFile_passes() {
        val board = Board.default
            .move(OpeningMoves.A4)
            .move(OpeningMoves.E5)
            .move(Move(PieceType.R.asWhite, from = Square.A1, to = Square.A3))
            .move(OpeningMoves.D5)
            .move(Move(PieceType.R.asWhite, from = Square.A3, to = Square.B3))
            .move(OpeningMoves.F5)
            .move(Move(PieceType.R.asWhite, from = Square.B3, to = Square.H3))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePe4Pe5Pd4Ke7_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.E5)
            .move(OpeningMoves.D4)
            .move(Move(PieceType.K.asBlack, from = Square.E8, to = Square.E7))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.WHITE_Q)
    }

    @Test
    fun movePe4Ph5Pd4Rh7_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.H5)
            .move(OpeningMoves.D4)
            .move(Move(PieceType.R.asBlack, from = Square.H8, to = Square.H7))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.WHITE_Q, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePe4Pa5Pd4Ra7_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.A5)
            .move(OpeningMoves.D4)
            .move(Move(PieceType.R.asBlack, from = Square.A8, to = Square.A7))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.WHITE_Q, CastlingOption.BLACK_K)
    }

    @Test
    fun movePe4Pe5Ke2Ke7_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.E5)
            .move(Move(PieceType.K.asWhite, from = Square.E1, to = Square.E2))
            .move(Move(PieceType.K.asBlack, from = Square.E8, to = Square.E7))

        assertThat(board.castlingOptions).isEmpty()
    }

    @Test
    fun enPassantA4A5_passes() {
        Board.default
            .move(OpeningMoves.A4)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.A3) }
            .move(OpeningMoves.A5)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.A6) }
    }

    @Test
    fun enPassantB4B5_passes() {
        Board.default
            .move(OpeningMoves.B4)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.B3) }
            .move(OpeningMoves.B5)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.B6) }
    }

    @Test
    fun enPassantC4C5_passes() {
        Board.default
            .move(OpeningMoves.C4)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.C3) }
            .move(OpeningMoves.C5)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.C6) }
    }

    @Test
    fun enPassantD4D5_passes() {
        Board.default
            .move(OpeningMoves.D4)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.D3) }
            .move(OpeningMoves.D5)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.D6) }
    }

    @Test
    fun enPassantE4E5_passes() {
        Board.default
            .move(OpeningMoves.E4)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.E3) }
            .move(OpeningMoves.E5)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.E6) }
    }

    @Test
    fun enPassantF4F5_passes() {
        Board.default
            .move(OpeningMoves.F4)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.F3) }
            .move(OpeningMoves.F5)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.F6) }
    }

    @Test
    fun enPassantG4G5_passes() {
        Board.default
            .move(OpeningMoves.G4)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.G3) }
            .move(OpeningMoves.G5)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.G6) }
    }

    @Test
    fun enPassantH4H5_passes() {
        Board.default
            .move(OpeningMoves.H4)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.H3) }
            .move(OpeningMoves.H5)
            .apply { assertThat(this.enPassant).isEqualTo(EnPassantOption.H6) }
    }
}
