// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.abra

import com.lz101010.chess.core.BoardGenerator
import com.lz101010.chess.data.Move
import com.lz101010.chess.data.Square
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PuzzleTest {
    @Test
    fun foolsMate_passes() {
        val board = BoardGenerator.fromFen("rnbqkbnr/ppppp2p/5p2/6p1/3PP3/8/PPP2PPP/RNBQKBNR w KQkq g6 0 3")

        assertThat(AbraEngine().nextMove(board))
            .isEqualTo(Move(Square.D1, Square.H5))
    }

    @Test
    fun smotheredMate_passes() {
        val board = BoardGenerator.fromFen("1k6/8/8/8/8/3K4/3R4/2R5 w - - 0 1")

        assertThat(AbraEngine().nextMove(board))
            .isEqualTo(Move(Square.D2, Square.B2))
    }

    @Test
    fun avoidBlunderingQueen_passes() {
        val board1 = BoardGenerator.fromFen("3k4/8/3K4/8/8/8/8/4Q3 w - - 0 1")

        assertThat(AbraEngine().nextMove(board1))
            .isIn(Move(Square.E1, Square.E7), Move(Square.E1, Square.E4), Move(Square.D6, Square.C6))

        val board2 = BoardGenerator.fromFen("r1bqkbnr/p1p1p2p/2n2pp1/3p3Q/1p1NP3/2NP4/PPP2PPP/R1B1KB1R w KQkq - 0 7")

        assertThat(AbraEngine().nextMove(board2))
            .isNotEqualTo(Move(Square.H5, Square.G6))
    }

    @Test
    fun avoidBlunderingMate_passes() {
        val board = BoardGenerator.fromFen("2k5/8/3K4/8/8/8/8/6Q1 w - - 0 1")

        assertThat(AbraEngine().nextMove(board))
            .isNotEqualTo(Move(Square.G1, Square.B6))
            .isIn(Move(Square.G1, Square.B1), Move(Square.G1, Square.A7))

        val board2 = BoardGenerator.fromFen("2k5/8/3K4/8/7Q/8/8/8 w - - 0 1")
        assertThat(AbraEngine().nextMove(board2))
            .isEqualTo(Move(Square.H4, Square.B4))
    }

    @Test
    fun blackCanOnlyLose_passes() {
        val board = BoardGenerator.fromFen("1k6/4Q3/2K5/8/8/8/8/8 b - - 0 1")

        assertThat(AbraEngine().nextMove(board))
            .isIn(Move(Square.B8, Square.A8), Move(Square.B8, Square.C8))
    }

    @Test
    fun blackLosing_passes() {
        val board = BoardGenerator.fromFen("1k6/3KQ3/8/8/8/8/8/8 b - - 0 1")

        assertThat(AbraEngine(5).nextMove(board))
            .isIn(Move(Square.B8, Square.A7), Move(Square.B8, Square.A8), Move(Square.B8, Square.B7))
    }

    @Test
    fun movesExist_passes() {
        val board = BoardGenerator.fromFen("1nbqk1nr/rppp3p/p4p2/4p1p1/4P2P/P1P4R/P2PQPP1/RNB1KBN1 w - - 0 1")

        assertThat(AbraEngine().nextMove(board)).isNotNull
    }

    @Test
    fun findKillerMove_passes() {
        val board = BoardGenerator.fromFen("rn2kb1r/ppp2p1p/2q4B/3NP1p1/4P3/7P/PPP2P1P/R2QKBNR w KQkq g6 0 8")

        assertThat(AbraEngine().nextMove(board))
            .isEqualTo(Move(Square.F1, Square.B5))
    }
}
