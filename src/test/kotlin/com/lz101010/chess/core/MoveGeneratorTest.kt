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
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class MoveGeneratorTest {
    @Test
    fun findEmptyBoardMoves_fails() {
        assertThrows<RuntimeException> { MoveGenerator.find(Board.empty) }
    }

    @Test
    fun findDefaultBoardMoves_passes() {
        assertThat(MoveGenerator.find(Board.default))
            .hasSize(20)
            .contains(OpeningMoves.A3)
            .contains(OpeningMoves.B3)
            .contains(OpeningMoves.C3)
            .contains(OpeningMoves.D3)
            .contains(OpeningMoves.E3)
            .contains(OpeningMoves.F3)
            .contains(OpeningMoves.G3)
            .contains(OpeningMoves.H3)
            .contains(OpeningMoves.A4)
            .contains(OpeningMoves.B4)
            .contains(OpeningMoves.C4)
            .contains(OpeningMoves.D4)
            .contains(OpeningMoves.E4)
            .contains(OpeningMoves.F4)
            .contains(OpeningMoves.G4)
            .contains(OpeningMoves.H4)
            .contains(OpeningMoves.Na3)
            .contains(OpeningMoves.Nc3)
            .contains(OpeningMoves.Nf3)
            .contains(OpeningMoves.Nh3)
    }

    @Test
    fun findBoardAfterE4Moves_passes() {
        val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)

        assertThat(MoveGenerator.find(boardAfterE4))
            .hasSize(20)
            .containsAll(movesAfterE4())
    }

    @Test
    fun findBoardAfterE4B5Moves_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.B5)

        assertThat(MoveGenerator.find(board))
            .hasSize(29)
            .containsAll(movesAfterE4E5().filterNot { it == Move(Square.F1, Square.A6) })
            .contains(Move(Square.E4, Square.E5))
    }

    @Test
    fun findBoardAfterE4D5Moves_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.D5)

        assertThat(MoveGenerator.find(board))
            .hasSize(31)
            .containsAll(movesAfterE4E5())
            .contains(Move(Square.E4, Square.D5))
            .contains(Move(Square.E4, Square.E5))
    }

    @Test
    fun findBoardAfterE4E5Moves_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.E5)

        assertThat(MoveGenerator.find(board))
            .hasSize(29)
            .containsAll(movesAfterE4E5())
    }

    @Test
    fun findBoardAfterE4F5Moves_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.F5)

        assertThat(MoveGenerator.find(board))
            .hasSize(31)
            .containsAll(movesAfterE4E5())
            .contains(Move(Square.E4, Square.F5))
            .contains(Move(Square.E4, Square.E5))
    }

    @ParameterizedTest
    @MethodSource("movesAfterE4")
    fun findBoardAfterE4NotBDEF5Moves_passes(move: Move) {
        if (move in listOf(OpeningMoves.B5, OpeningMoves.D5, OpeningMoves.E5, OpeningMoves.F5)) {
            return
        }
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(move)

        assertThat(MoveGenerator.find(board))
            .hasSize(30)
            .containsAll(movesAfterE4E5())
            .contains(Move(Square.E4, Square.E5))
    }

    @Test
    fun findPawnPromotion_passes() {
        val board = BoardGenerator.fromFen("8/2P5/4K3/8/8/1k6/8/8 w - - 0 1")
        assertThat(MoveGenerator.find(board))
            .hasSize(12)
            .contains(Move(Square.C7, Square.C8, PieceType.Q))
            .contains(Move(Square.C7, Square.C8, PieceType.R))
            .contains(Move(Square.C7, Square.C8, PieceType.B))
            .contains(Move(Square.C7, Square.C8, PieceType.N))
    }

    @Test
    fun findCastling_passes() {
        val board = BoardGenerator.fromFen("4k3/8/8/8/8/8/8/R3K2R w KQ - 0 1")

        assertThat(MoveGenerator.find(board))
            .contains(Move(Square.E1, Square.G1))
            .contains(Move(Square.E1, Square.C1))
    }

    @Test
    fun findEnPassant_passes() {
        val board = BoardGenerator.fromFen("rnbqkbnr/pppp2pp/4p3/4Pp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 3")

        assertThat(MoveGenerator.find(board))
            .contains(Move(Square.E5, Square.F6))
    }

    companion object {
        @JvmStatic
        private fun movesAfterE4(): List<Move> {
            return listOf(
                OpeningMoves.A6,
                OpeningMoves.B6,
                OpeningMoves.C6,
                OpeningMoves.D6,
                OpeningMoves.E6,
                OpeningMoves.F6,
                OpeningMoves.G6,
                OpeningMoves.H6,
                OpeningMoves.A5,
                OpeningMoves.B5,
                OpeningMoves.C5,
                OpeningMoves.D5,
                OpeningMoves.E5,
                OpeningMoves.F5,
                OpeningMoves.G5,
                OpeningMoves.H5,
                OpeningMoves.Na6,
                OpeningMoves.Nc6,
                OpeningMoves.Nf6,
                OpeningMoves.Nh6
            )
        }

        @JvmStatic
        private fun movesAfterE4E5(): List<Move> {
            return listOf(
                OpeningMoves.A3,
                OpeningMoves.B3,
                OpeningMoves.C3,
                OpeningMoves.D3,
                OpeningMoves.F3,
                OpeningMoves.G3,
                OpeningMoves.H3,
                OpeningMoves.A4,
                OpeningMoves.B4,
                OpeningMoves.C4,
                OpeningMoves.D4,
                OpeningMoves.F4,
                OpeningMoves.G4,
                OpeningMoves.H4,
                OpeningMoves.Na3,
                OpeningMoves.Nc3,
                OpeningMoves.Nf3,
                OpeningMoves.Nh3,
                Move(Square.G1, Square.E2),
                Move(Square.F1, Square.E2),
                Move(Square.F1, Square.D3),
                Move(Square.F1, Square.C4),
                Move(Square.F1, Square.B5),
                Move(Square.F1, Square.A6),
                Move(Square.D1, Square.E2),
                Move(Square.D1, Square.F3),
                Move(Square.D1, Square.G4),
                Move(Square.D1, Square.H5),
                Move(Square.E1, Square.E2)
            )
        }
    }
}
