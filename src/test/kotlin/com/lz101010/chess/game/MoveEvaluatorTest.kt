// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.game

import com.lz101010.chess.data.Square
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class MoveEvaluatorTest {
    @Test
    fun detectCheck_passes() {
        val move = Move(Square.F5, Square.F8)

        runAssertions("q2k4/8/8/5Q2/8/8/8/3K4 w - - 0 1", move, check = true)
    }

    @Test
    fun detectMate_passes() {
        val move = Move(Square.E6, Square.D7)

        runAssertions("q2k4/8/3KQ3/8/8/8/8/8 w - - 0 1", move, check = true, mate = true)
    }

    @Test
    fun detectTechnicalMate_passes() {
        val move = Move(Square.H7, Square.H8)

        runAssertions("k6q/2K4Q/8/8/8/8/8/8 w - - 0 1", move, check = true, technicalMate = true)
    }

    @Test
    fun detectTechnicalMateWithoutCheck_passes() {
        val move = Move(Square.F5, Square.F6)

        runAssertions("8/1k6/8/3NBK2/8/8/8/8 w - - 0 1", move, technicalMate = true)
        runAssertions("8/1k6/8/4BK1B/8/8/8/8 w - - 0 1", move, technicalMate = true)
        runAssertions("8/1k6/8/3NNK2/8/8/8/8 w - - 0 1", move, technicalMate = true)
        runAssertions("8/1k6/8/4RK2/8/8/8/8 w - - 0 1", move, technicalMate = true)
        runAssertions("8/bk6/8/4QK2/8/8/8/8 w - - 0 1", move, technicalMate = true)
        runAssertions("8/nk6/8/4QK2/8/8/8/8 w - - 0 1", move, technicalMate = true)
    }

    @Test
    fun detectNotTechnicalMate_passes() {
        val move = Move(Square.F5, Square.F6)

        runAssertions("8/rk6/8/4BK1B/8/8/8/8 w - - 0 1", move, technicalMate = false)
        runAssertions("8/rk6/8/4NK2/8/8/8/8 w - - 0 1", move, technicalMate = false)
        runAssertions("8/qk6/8/4NK2/8/8/8/8 w - - 0 1", move, technicalMate = false)
        runAssertions("8/rk6/8/4BK2/8/8/8/8 w - - 0 1", move, technicalMate = false)
        runAssertions("8/qk6/8/4BK2/8/8/8/8 w - - 0 1", move, technicalMate = false)
    }

    @Test
    fun detectStaleMate_passes() {
        val move = Move(Square.H6, Square.H7)

        runAssertions("k1K5/8/5p1Q/5P2/8/8/8/8 w - - 0 1", move, staleMate = true)
    }

    @Test
    fun detectTechnicalDraw_passes() {
        val move = Move(Square.G8, Square.H8)

        runAssertions("6Kq/4k3/8/8/8/8/8/8 w - - 0 1", move, technicalDraw = true)
    }

    @Test
    fun detectNotTechnicalDraw_passes() {
        val move = Move(Square.G8, Square.H8)

        runAssertions("6K1/rk6/8/4N3/8/8/8/8 w - - 0 1", move, technicalDraw = false)
        runAssertions("6K1/rk6/8/4R3/8/8/8/8 w - - 0 1", move, technicalDraw = false)
    }

    @ParameterizedTest
    @MethodSource("invalidCastlingMoves")
    fun castlingWithInvalidMove_passes(move: Move) {
        val board = BoardGenerator.fromFen("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1")

        assertThat(MoveEvaluator.isKingSideCastleWhite(Board.default, move)).isFalse
        assertThat(MoveEvaluator.isKingSideCastleBlack(Board.default, move)).isFalse
        assertThat(MoveEvaluator.isQueenSideCastleWhite(Board.default, move)).isFalse
        assertThat(MoveEvaluator.isQueenSideCastleBlack(Board.default, move)).isFalse

        assertThat(MoveEvaluator.isKingSideCastleWhite(board, move)).isFalse
        assertThat(MoveEvaluator.isKingSideCastleBlack(board, move)).isFalse
        assertThat(MoveEvaluator.isQueenSideCastleWhite(board, move)).isFalse
        assertThat(MoveEvaluator.isQueenSideCastleBlack(board, move)).isFalse
    }

    companion object {
        @JvmStatic
        private fun invalidCastlingMoves(): List<Move> {
            return listOf(
                Move(Square.A3, Square.A4),
                Move(Square.A1, Square.A2),
                Move(Square.A8, Square.A7),
                Move(Square.E1, Square.E2),
                Move(Square.E8, Square.E7)
            )
        }
    }

    private fun runAssertions(
        fen: String,
        move: Move,
        check: Boolean = false,
        mate: Boolean = false,
        technicalMate: Boolean = false,
        staleMate: Boolean = false,
        technicalDraw: Boolean = false
    ) {
        val board = BoardGenerator.fromFen(fen)
        assertThat(MoveEvaluator.isCheck(board, move)).isEqualTo(check)
        assertThat(MoveEvaluator.isMate(board, move)).isEqualTo(mate)
        assertThat(MoveEvaluator.isTechnicalMate(board, move)).isEqualTo(technicalMate)
        assertThat(MoveEvaluator.isStaleMate(board, move)).isEqualTo(staleMate)
        assertThat(MoveEvaluator.isTechnicalDraw(board, move)).isEqualTo(technicalDraw)
    }
}
