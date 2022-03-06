// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import com.lz101010.chess.data.PieceType
import com.lz101010.chess.data.Square
import com.lz101010.chess.support.OpeningMoves
import com.lz101010.chess.support.move
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PositionalScoreGeneratorTest {
    @Test
    fun matedScore_passes() {
        assertThat(score("3k4/3Q4/3K4/8/8/8/8/8 b - - 0 1")).isEqualTo(Int.MIN_VALUE)
    }

    @Test
    fun staleMatedScore_passes() {
        assertThat(score("k1K5/7Q/8/8/8/8/8/8 b - - 0 1")).isEqualTo(0)
    }

    @Test
    fun matePossibleScore_passes() {
        assertThat(score("3k4/8/3K4/5Q2/8/8/8/8 w - - 0 1")).isEqualTo(Int.MAX_VALUE)
    }

    @Test
    fun defaultBoardScore_passes() {
        assertThat(ScoreGenerator.positional(Board.default)).isEqualTo(0)
    }

    @Test
    fun boardAfterE4Score_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)

        assertThat(ScoreGenerator.positional(board)).isEqualTo(-40)
    }

    @Test
    fun boardAfterE4D5Score_passes() {
        val board = Board.default
            .move(OpeningMoves.E4).move(OpeningMoves.D5)

        assertThat(ScoreGenerator.positional(board)).isEqualTo(0)
    }

    @Test
    fun boardAfterE4xD5Score_passes() {
        val board = Board.default
            .move(OpeningMoves.E4).move(OpeningMoves.D5).move(Move(PieceType.P.asWhite, Square.E4, Square.D5))

        assertThat(ScoreGenerator.positional(board)).isEqualTo(-25)
    }

    @Test
    fun boardAfterQxD5Score_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.D5)
            .move(Move(PieceType.P.asWhite, Square.E4, Square.D5))
            .move(Move(PieceType.Q.asBlack, Square.D8, Square.D5))

        assertThat(ScoreGenerator.positional(board)).isEqualTo(-10)
    }

    @Test
    fun endgameBoards_passes() {
        assertThat(score("k7/6QK/8/8/8/8/8/8 w - - 0 1")).isEqualTo(+20)
        assertThat(score("K7/6qk/8/8/8/8/8/8 w - - 0 1")).isEqualTo(-20)
        assertThat(score("k7/8/5RQK/8/8/8/8/8 w - - 0 1")).isEqualTo(-50) // absurd, but constructed
        assertThat(score("K7/8/5rqk/8/8/8/8/8 w - - 0 1")).isEqualTo(-20)
        assertThat(score("k7/8/5PQK/8/8/8/8/8 w - - 0 1")).isEqualTo(-30) // absurd, but constructed
        assertThat(score("K7/8/5pqk/8/8/8/8/8 w - - 0 1")).isEqualTo(-10)
    }

    private fun score(fen: String): Int {
        val board = BoardGenerator.fromFen(fen)
        return ScoreGenerator.positional(board)
    }
}
