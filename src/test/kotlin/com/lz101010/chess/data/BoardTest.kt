// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun initialBoardPrints_passes() {
        assertThat(Board.default.toString()).isEqualTo("""
            r n b q k b n r
            p p p p p p p p
            - - - - - - - -
            - - - - - - - -
            - - - - - - - -
            - - - - - - - -
            P P P P P P P P
            R N B Q K B N R
        """.trimIndent())
    }

    @Test
    fun basicEqualityAndHashCheck_passes() {
        val board1 = Board()
        val board2 = Board()
        assertThat(board1).isEqualTo(board1)
        assertThat(board1).isEqualTo(board2)
        assertThat(board1.hashCode()).isEqualTo(board2.hashCode())

        assertThat(board1).isNotEqualTo(PieceType.B)
        assertThat(board1).isNotEqualTo(Board.empty)
        assertThat(board1).isNotEqualTo(null)
        assertThat(Board(whiteToMove = true)).isNotEqualTo(Board(whiteToMove = false))
        assertThat(Board(whiteToMove = true).hashCode()).isNotEqualTo(Board(whiteToMove = false).hashCode())
    }

    @Test
    fun equalityAndHashCheckWithAllPropertiesVaried_passes() {
        val board = Board()

        testHashAndEquality_forEqualBoards(board, board.copy(whiteToMove = true))
        testHashAndEquality_forDifferentBoards(board, board.copy(whiteToMove = false))

        testHashAndEquality_forEqualBoards(board, board.copy(castlingOptions = CastlingOption.values().asList()))
        testHashAndEquality_forDifferentBoards(board, board.copy(castlingOptions = listOf()))

        testHashAndEquality_forEqualBoards(board, board.copy(enPassant = null))
        testHashAndEquality_forDifferentBoards(board, board.copy(enPassant = EnPassantOption.A3))

        testHashAndEquality_forEqualBoards(board, board.copy(plies = 0u))
        testHashAndEquality_forDifferentBoards(board, board.copy(plies = 1u))

        testHashAndEquality_forEqualBoards(board, board.copy(nextMove = 1u))
        testHashAndEquality_forDifferentBoards(board, board.copy(nextMove = 0u))
    }

    private fun testHashAndEquality_forEqualBoards(board1: Board, board2: Board) {
        assertThat(board1).isEqualTo(board2)
        assertThat(board1.hashCode()).isEqualTo(board2.hashCode())
    }

    private fun testHashAndEquality_forDifferentBoards(board1: Board, board2: Board) {
        assertThat(board1).isNotEqualTo(board2)
        assertThat(board1.hashCode()).isNotEqualTo(board2.hashCode())
    }

    @Test
    fun initialEvalScore_passes() {
        val initialScore = 8 + 9 + 6 + 6 + 10
        assertThat(Board(whiteToMove = true).evalScore()).isEqualTo(initialScore)
        assertThat(Board(whiteToMove = false).evalScore()).isEqualTo(initialScore)
    }

    @Test
    fun initialBoardColors_passes() {
        for (index in 0..7) {
            assertThat(Board.default.pieces[0][0]!!.black).isTrue
            assertThat(Board.default.pieces[1][0]!!.black).isTrue
            assertThat(Board.default.pieces[2][0]).isNull()
            assertThat(Board.default.pieces[3][0]).isNull()
            assertThat(Board.default.pieces[4][0]).isNull()
            assertThat(Board.default.pieces[5][0]).isNull()
            assertThat(Board.default.pieces[6][0]!!.white).isTrue
            assertThat(Board.default.pieces[7][0]!!.white).isTrue
        }
    }
}
