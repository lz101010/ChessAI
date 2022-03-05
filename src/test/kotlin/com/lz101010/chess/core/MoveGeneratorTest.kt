// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import com.lz101010.chess.data.PieceType
import com.lz101010.chess.data.Square
import com.lz101010.chess.support.OpeningMoves
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
        assertThat(MoveGenerator.find(Board()))
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
        val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)
        val boardAfterB5 = MoveMaker.move(boardAfterE4, OpeningMoves.B5)

        assertThat(MoveGenerator.find(boardAfterB5))
            .hasSize(29)
            .containsAll(movesAfterE4E5().filterNot { it == Move(PieceType.B.asWhite, Square.F1, Square.A6) })
            .contains(Move(PieceType.P.asWhite, Square.E4, Square.E5))
    }

    @Test
    fun findBoardAfterE4D5Moves_passes() {
        val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)
        val boardAfterD5 = MoveMaker.move(boardAfterE4, OpeningMoves.D5)

        assertThat(MoveGenerator.find(boardAfterD5))
            .hasSize(31)
            .containsAll(movesAfterE4E5())
            .contains(Move(PieceType.P.asWhite, Square.E4, Square.D5))
            .contains(Move(PieceType.P.asWhite, Square.E4, Square.E5))
    }

    @Test
    fun findBoardAfterE4E5Moves_passes() {
        val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)
        val boardAfterE5 = MoveMaker.move(boardAfterE4, OpeningMoves.E5)

        assertThat(MoveGenerator.find(boardAfterE5))
            .hasSize(29)
            .containsAll(movesAfterE4E5())
    }

    @Test
    fun findBoardAfterE4F5Moves_passes() {
        val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)
        val boardAfterF5 = MoveMaker.move(boardAfterE4, OpeningMoves.F5)

        assertThat(MoveGenerator.find(boardAfterF5))
            .hasSize(31)
            .containsAll(movesAfterE4E5())
            .contains(Move(PieceType.P.asWhite, Square.E4, Square.F5))
            .contains(Move(PieceType.P.asWhite, Square.E4, Square.E5))
    }

    @ParameterizedTest
    @MethodSource("movesAfterE4")
    fun findBoardAfterE4NotBDEF5Moves_passes(move: Move) {
        if (move in listOf(OpeningMoves.B5, OpeningMoves.D5, OpeningMoves.E5, OpeningMoves.F5)) {
            return
        }
        val boardAfterE4 = MoveMaker.move(Board.default, OpeningMoves.E4)
        val boardAfterE5 = MoveMaker.move(boardAfterE4, move)

        assertThat(MoveGenerator.find(boardAfterE5))
            .hasSize(30)
            .containsAll(movesAfterE4E5())
            .contains(Move(PieceType.P.asWhite, Square.E4, Square.E5))
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
                Move(PieceType.N.asWhite, Square.G1, Square.E2),
                Move(PieceType.B.asWhite, Square.F1, Square.E2),
                Move(PieceType.B.asWhite, Square.F1, Square.D3),
                Move(PieceType.B.asWhite, Square.F1, Square.C4),
                Move(PieceType.B.asWhite, Square.F1, Square.B5),
                Move(PieceType.B.asWhite, Square.F1, Square.A6),
                Move(PieceType.Q.asWhite, Square.D1, Square.E2),
                Move(PieceType.Q.asWhite, Square.D1, Square.F3),
                Move(PieceType.Q.asWhite, Square.D1, Square.G4),
                Move(PieceType.Q.asWhite, Square.D1, Square.H5),
                Move(PieceType.K.asWhite, Square.E1, Square.E2)
            )
        }
    }
}
