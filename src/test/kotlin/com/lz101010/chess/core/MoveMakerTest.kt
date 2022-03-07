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
            Board.default.move(Move(Square.F1, Square.E2))
        }.let {
            assertThat(it.message).isEqualTo("${Square.E2} is occupied by same color piece")
        }
    }

    @Test
    fun moveF3E3_fails() {
        assertThrows<IllegalArgumentException> {
            Board.default.move(Move(Square.F3, Square.E3))
        }.let {
            assertThat(it.message).isEqualTo("f3 is empty")
        }
    }

    @Test
    fun moveNg8f6_fails() {
        assertThrows<IllegalArgumentException> {
            Board.default.move(Move(Square.G8, Square.F6))
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
            .move(Move(Square.E1, Square.E2))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePh4Pe5Rh2_passes() {
        val board = Board.default
            .move(OpeningMoves.H4)
            .move(OpeningMoves.E5)
            .move(Move(Square.H1, Square.H2))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_Q, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePa4Pe5Ra2_passes() {
        val board = Board.default
            .move(OpeningMoves.A4)
            .move(OpeningMoves.E5)
            .move(Move(Square.A1, Square.A2))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun moveRookFromBFile_passes() {
        val board = Board.default
            .move(OpeningMoves.A4)
            .move(OpeningMoves.E5)
            .move(Move(Square.A1, Square.A3))
            .move(OpeningMoves.D5)
            .move(Move(Square.A3, Square.B3))
            .move(OpeningMoves.F5)
            .move(Move(Square.B3, Square.H3))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePe4Pe5Pd4Ke7_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.E5)
            .move(OpeningMoves.D4)
            .move(Move(Square.E8, Square.E7))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.WHITE_Q)
    }

    @Test
    fun movePe4Ph5Pd4Rh7_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.H5)
            .move(OpeningMoves.D4)
            .move(Move(Square.H8, Square.H7))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.WHITE_Q, CastlingOption.BLACK_Q)
    }

    @Test
    fun movePe4Pa5Pd4Ra7_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.A5)
            .move(OpeningMoves.D4)
            .move(Move(Square.A8, Square.A7))

        assertThat(board.castlingOptions)
            .containsExactlyInAnyOrder(CastlingOption.WHITE_K, CastlingOption.WHITE_Q, CastlingOption.BLACK_K)
    }

    @Test
    fun movePe4Pe5Ke2Ke7_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.E5)
            .move(Move(Square.E1, Square.E2))
            .move(Move(Square.E8, Square.E7))

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

    @Test
    fun promotion_passes() {
        val board = BoardGenerator.fromFen("8/2P5/4K3/8/8/1k6/8/8 w - - 0 1")
            .move(Move(Square.C7, Square.C8, promotion = PieceType.Q))

        assertThat(board).isEqualTo(BoardGenerator.fromFen("2Q5/8/4K3/8/8/1k6/8/8 b - - 0 1"))
    }

    @Test
    fun castlingKingSideWhite_passes() {
        val board = BoardGenerator.fromFen("4k3/8/8/8/8/8/8/R3K2R w KQ - 0 1")
            .move(Move(Square.E1, Square.G1))

        assertThat(board).isEqualTo(BoardGenerator.fromFen("4k3/8/8/8/8/8/8/R4RK1 b - - 1 1"))
    }

    @Test
    fun castlingKingSideBlack_passes() {
        val board = BoardGenerator.fromFen("r3k2r/8/8/8/8/8/8/4K3 b kq - 0 1")
            .move(Move(Square.E8, Square.G8))

        assertThat(board).isEqualTo(BoardGenerator.fromFen("r4rk1/8/8/8/8/8/8/4K3 w - - 1 2"))
    }

    @Test
    fun castlingQueenSideWhite_passes() {
        val board = BoardGenerator.fromFen("4k3/8/8/8/8/8/8/R3K2R w KQ - 0 1")
            .move(Move(Square.E1, Square.C1))

        assertThat(board).isEqualTo(BoardGenerator.fromFen("4k3/8/8/8/8/8/8/2KR3R b - - 1 1"))
    }

    @Test
    fun castlingQueenSideBlack_passes() {
        val board = BoardGenerator.fromFen("r3k2r/8/8/8/8/8/8/4K3 b kq - 0 1")
            .move(Move(Square.E8, Square.C8))

        assertThat(board).isEqualTo(BoardGenerator.fromFen("2kr3r/8/8/8/8/8/8/4K3 w - - 1 2"))
    }

    @Test
    fun enPassantCaptureWhite_passes() {
        val board = BoardGenerator.fromFen("rnbqkbnr/pppp2pp/4p3/4Pp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 3")
            .move(Move(Square.E5, Square.F6))

        assertThat(board).isEqualTo(BoardGenerator.fromFen("rnbqkbnr/pppp2pp/4pP2/8/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 3"))
    }

    @Test
    fun enPassantCaptureBlack_passes() {
        val board = BoardGenerator.fromFen("rnbqkbnr/ppp1pppp/8/8/3pP3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 3")
            .move(Move(Square.D4, Square.E3))

        assertThat(board).isEqualTo(BoardGenerator.fromFen("rnbqkbnr/ppp1pppp/8/8/8/4p3/PPPP1PPP/RNBQKBNR w KQkq - 0 4"))
    }

    @Test
    fun rookMovingAndCapturePreventsCastlingQk_passes() {
        val board = Board.default
            .move(OpeningMoves.A4)
            .move(OpeningMoves.H5)
            .move(OpeningMoves.G4)
            .move(Move(Square.H5, Square.G4))
            .move(Move(Square.A1, Square.A3))
            .move(OpeningMoves.G5)
            .move(Move(Square.A3, Square.H3))
            .move(OpeningMoves.F5)
            .move(Move(Square.H3, Square.H8))

        assertThat(board)
            .isEqualTo(BoardGenerator.fromFen("rnbqkbnR/ppppp3/8/5pp1/P5p1/8/1PPPPP1P/1NBQKBNR b Kq - 0 5"))
    }

    @Test
    fun rookMovingAndCapturePreventsCastlingQq_passes() {
        val board = Board.default
            .move(OpeningMoves.A4)
            .move(OpeningMoves.A5)
            .move(OpeningMoves.B4)
            .move(OpeningMoves.B5)
            .move(Move(Square.A4, Square.B5))
            .move(Move(Square.A5, Square.B4))
            .move(Move(Square.A1, Square.A8))

        assertThat(board)
            .isEqualTo(BoardGenerator.fromFen("Rnbqkbnr/2pppppp/8/1P6/1p6/8/2PPPPPP/1NBQKBNR b Kk - 0 4"))
    }
}
