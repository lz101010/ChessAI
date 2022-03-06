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

class ScoreGeneratorTest {
    @Test
    fun matedScore_passes() {
        val board = BoardGenerator.fromFen("3k4/3Q4/3K4/8/8/8/8/8 b - - 0 1")

        assertThat(ScoreGenerator.simpleScore(board)).isEqualTo(Int.MIN_VALUE)
    }

    @Test
    fun staleMatedScore_passes() {
        val board = BoardGenerator.fromFen("k1K5/7Q/8/8/8/8/8/8 b - - 0 1")

        assertThat(ScoreGenerator.simpleScore(board)).isEqualTo(0)
    }

    @Test
    fun matePossibleScore_passes() {
        val board = BoardGenerator.fromFen("3k4/8/3K4/5Q2/8/8/8/8 w - - 0 1")

        assertThat(ScoreGenerator.simpleScore(board)).isEqualTo(Int.MAX_VALUE)
    }

    @Test
    fun defaultBoardScore_passes() {
        assertThat(ScoreGenerator.simpleScore(Board.default)).isEqualTo(0)
    }

    @Test
    fun boardAfterE4xD5Score_passes() {
        val board = Board.default
            .move(OpeningMoves.E4).move(OpeningMoves.D5).move(Move(PieceType.P.asWhite, Square.E4, Square.D5))

        assertThat(ScoreGenerator.simpleScore(board)).isEqualTo(-1)
    }

    @Test
    fun boardAfterQxD5Score_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.D5)
            .move(Move(PieceType.P.asWhite, Square.E4, Square.D5))
            .move(Move(PieceType.Q.asBlack, Square.D8, Square.D5))

        assertThat(ScoreGenerator.simpleScore(board)).isEqualTo(0)
    }
}
