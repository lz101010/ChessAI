// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.*
import com.lz101010.chess.support.OpeningMoves
import com.lz101010.chess.support.move
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class FenGeneratorTest {
    @Test
    fun emptyBoardFen_passes() {
        assertDoesNotThrow { FenGenerator.generate(Board.empty) }
    }

    @Test
    fun defaultBoardFen_passes() {
        assertThat(FenGenerator.generate(Board.default))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
    }

    @Test
    fun e4Fen_passes() {
        val defaultBoard = Board.default
        val boardAfterE4 = MoveMaker.move(defaultBoard, OpeningMoves.E4)

        assertThat(FenGenerator.generate(boardAfterE4))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1")
    }

    @Test
    fun e4e5Fen_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.E5)

        assertThat(FenGenerator.generate(board))
            .isEqualTo("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2")
    }

    @Test
    fun e4Nh6Fen_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.Nh6)

        assertThat(FenGenerator.generate(board))
            .isEqualTo("rnbqkb1r/pppppppp/7n/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 1 2")
    }

    @Test
    fun e4Nf6Fen_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.Nf6)
            .move(OpeningMoves.D4)
            .move(Move(PieceType.N.asBlack, Square.F6, Square.E4))

        assertThat(FenGenerator.generate(board))
            .isEqualTo("rnbqkb1r/pppppppp/8/8/3Pn3/8/PPP2PPP/RNBQKBNR w KQkq - 0 3")
    }

    @ParameterizedTest
    @EnumSource(value = EnPassantOption::class)
    fun enPassantOption_passes(option: EnPassantOption) {
        assertThat(FenGenerator.generate(Board(enPassant = option)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq ${option.name.lowercase()} 0 1")
    }

    @Test
    fun tooManyCastlingOption_passes() {
        val tooManyCastlingOptions = listOf(
            CastlingOption.BLACK_K,
            CastlingOption.BLACK_Q,
            CastlingOption.WHITE_K,
            CastlingOption.WHITE_Q,
            CastlingOption.WHITE_Q
        )
        assertThat(FenGenerator.generate(Board(castlingOptions = tooManyCastlingOptions)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
    }

    @Test
    fun fewerCastlingOption_passes() {
        val noK = listOf(CastlingOption.BLACK_K, CastlingOption.BLACK_Q, CastlingOption.WHITE_Q)
        val noQ = listOf(CastlingOption.BLACK_K, CastlingOption.BLACK_Q, CastlingOption.WHITE_K)
        val nok = listOf(CastlingOption.BLACK_Q, CastlingOption.WHITE_K, CastlingOption.WHITE_Q)
        val noq = listOf(CastlingOption.BLACK_K, CastlingOption.WHITE_K, CastlingOption.WHITE_Q)

        val noKQ = listOf(CastlingOption.BLACK_K, CastlingOption.BLACK_Q)
        val nokq = listOf(CastlingOption.WHITE_K, CastlingOption.WHITE_Q)
        val noKk = listOf(CastlingOption.BLACK_Q, CastlingOption.WHITE_Q)
        val noKq = listOf(CastlingOption.BLACK_K, CastlingOption.WHITE_Q)
        val noQk = listOf(CastlingOption.BLACK_Q, CastlingOption.WHITE_K)
        val noQq = listOf(CastlingOption.BLACK_K, CastlingOption.WHITE_K)

        val noKQk = listOf(CastlingOption.BLACK_Q)
        val noKQq = listOf(CastlingOption.BLACK_K)
        val noKkq = listOf(CastlingOption.WHITE_Q)
        val noQkq = listOf(CastlingOption.WHITE_K)

        val noKQkq = listOf<CastlingOption>()

        assertThat(FenGenerator.generate(Board(castlingOptions = noK)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Qkq - 0 1")
        assertThat(FenGenerator.generate(Board(castlingOptions = noQ)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Kkq - 0 1")
        assertThat(FenGenerator.generate(Board(castlingOptions = nok)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQq - 0 1")
        assertThat(FenGenerator.generate(Board(castlingOptions = noq)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQk - 0 1")

        assertThat(FenGenerator.generate(Board(castlingOptions = noKQ)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w kq - 0 1")
        assertThat(FenGenerator.generate(Board(castlingOptions = nokq)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQ - 0 1")
        assertThat(FenGenerator.generate(Board(castlingOptions = noKk)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Qq - 0 1")
        assertThat(FenGenerator.generate(Board(castlingOptions = noKq)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Qk - 0 1")
        assertThat(FenGenerator.generate(Board(castlingOptions = noQk)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Kq - 0 1")
        assertThat(FenGenerator.generate(Board(castlingOptions = noQq)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Kk - 0 1")

        assertThat(FenGenerator.generate(Board(castlingOptions = noKQk)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w q - 0 1")
        assertThat(FenGenerator.generate(Board(castlingOptions = noKQq)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w k - 0 1")
        assertThat(FenGenerator.generate(Board(castlingOptions = noKkq)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Q - 0 1")
        assertThat(FenGenerator.generate(Board(castlingOptions = noQkq)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w K - 0 1")

        assertThat(FenGenerator.generate(Board(castlingOptions = noKQkq)))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1")
    }
}
