// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import com.lz101010.chess.data.PieceType
import com.lz101010.chess.data.Square
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
            .contains(Move(PieceType.P.asWhite, Square.A2, Square.A3))
            .contains(Move(PieceType.P.asWhite, Square.B2, Square.B3))
            .contains(Move(PieceType.P.asWhite, Square.C2, Square.C3))
            .contains(Move(PieceType.P.asWhite, Square.D2, Square.D3))
            .contains(Move(PieceType.P.asWhite, Square.E2, Square.E3))
            .contains(Move(PieceType.P.asWhite, Square.F2, Square.F3))
            .contains(Move(PieceType.P.asWhite, Square.G2, Square.G3))
            .contains(Move(PieceType.P.asWhite, Square.H2, Square.H3))
            .contains(Move(PieceType.P.asWhite, Square.A2, Square.A4))
            .contains(Move(PieceType.P.asWhite, Square.B2, Square.B4))
            .contains(Move(PieceType.P.asWhite, Square.C2, Square.C4))
            .contains(Move(PieceType.P.asWhite, Square.D2, Square.D4))
            .contains(Move(PieceType.P.asWhite, Square.E2, Square.E4))
            .contains(Move(PieceType.P.asWhite, Square.F2, Square.F4))
            .contains(Move(PieceType.P.asWhite, Square.G2, Square.G4))
            .contains(Move(PieceType.P.asWhite, Square.H2, Square.H4))
            .contains(Move(PieceType.N.asWhite, Square.B1, Square.A3))
            .contains(Move(PieceType.N.asWhite, Square.B1, Square.C3))
            .contains(Move(PieceType.N.asWhite, Square.G1, Square.F3))
            .contains(Move(PieceType.N.asWhite, Square.G1, Square.H3))
    }

    @Test
    fun findBoardAfterE4Moves_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, MOVE_E4)

        assertThat(MoveGenerator.find(boardAfterE4))
            .hasSize(20)
            .containsAll(movesAfterE4())
    }

    @Test
    fun findBoardAfterE4B5Moves_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, MOVE_E4)
        val boardAfterB5 = MoveMaker.move(boardAfterE4, MOVE_B5)

        assertThat(MoveGenerator.find(boardAfterB5))
            .hasSize(29)
            .containsAll(movesAfterE4E5().filterNot { it == Move(PieceType.B.asWhite, Square.F1, Square.A6) })
            .contains(Move(PieceType.P.asWhite, Square.E4, Square.E5))
    }

    @Test
    fun findBoardAfterE4D5Moves_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, MOVE_E4)
        val boardAfterD5 = MoveMaker.move(boardAfterE4, MOVE_D5)

        assertThat(MoveGenerator.find(boardAfterD5))
            .hasSize(31)
            .containsAll(movesAfterE4E5())
            .contains(Move(PieceType.P.asWhite, Square.E4, Square.D5))
            .contains(Move(PieceType.P.asWhite, Square.E4, Square.E5))
    }

    @Test
    fun findBoardAfterE4E5Moves_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, MOVE_E4)
        val boardAfterE5 = MoveMaker.move(boardAfterE4, MOVE_E5)

        assertThat(MoveGenerator.find(boardAfterE5))
            .hasSize(29)
            .containsAll(movesAfterE4E5())
    }

    @Test
    fun findBoardAfterE4F5Moves_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, MOVE_E4)
        val boardAfterF5 = MoveMaker.move(boardAfterE4, MOVE_F5)

        assertThat(MoveGenerator.find(boardAfterF5))
            .hasSize(31)
            .containsAll(movesAfterE4E5())
            .contains(Move(PieceType.P.asWhite, Square.E4, Square.F5))
            .contains(Move(PieceType.P.asWhite, Square.E4, Square.E5))
    }

    @ParameterizedTest
    @MethodSource("movesAfterE4")
    fun findBoardAfterE4NotBDEF5Moves_passes(move: Move) {
        if (move in listOf(MOVE_B5, MOVE_D5, MOVE_E5, MOVE_F5)) {
            return
        }
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, MOVE_E4)
        val boardAfterE5 = MoveMaker.move(boardAfterE4, move)

        assertThat(MoveGenerator.find(boardAfterE5))
            .hasSize(30)
            .containsAll(movesAfterE4E5())
            .contains(Move(PieceType.P.asWhite, Square.E4, Square.E5))
    }

    companion object {
        val MOVE_E4 = Move(PieceType.P.asWhite, from = Square.E2, to = Square.E4)

        val MOVE_B5 = Move(PieceType.P.asBlack, from = Square.B7, to = Square.B5)
        val MOVE_D5 = Move(PieceType.P.asBlack, from = Square.D7, to = Square.D5)
        val MOVE_E5 = Move(PieceType.P.asBlack, from = Square.E7, to = Square.E5)
        val MOVE_F5 = Move(PieceType.P.asBlack, from = Square.F7, to = Square.F5)

        @JvmStatic
        private fun movesAfterE4(): List<Move> {
            return listOf(
                Move(PieceType.P.asBlack, Square.A7, Square.A6),
                Move(PieceType.P.asBlack, Square.B7, Square.B6),
                Move(PieceType.P.asBlack, Square.C7, Square.C6),
                Move(PieceType.P.asBlack, Square.D7, Square.D6),
                Move(PieceType.P.asBlack, Square.E7, Square.E6),
                Move(PieceType.P.asBlack, Square.F7, Square.F6),
                Move(PieceType.P.asBlack, Square.G7, Square.G6),
                Move(PieceType.P.asBlack, Square.H7, Square.H6),
                Move(PieceType.P.asBlack, Square.A7, Square.A5),
                Move(PieceType.P.asBlack, Square.B7, Square.B5),
                Move(PieceType.P.asBlack, Square.C7, Square.C5),
                Move(PieceType.P.asBlack, Square.D7, Square.D5),
                Move(PieceType.P.asBlack, Square.E7, Square.E5),
                Move(PieceType.P.asBlack, Square.F7, Square.F5),
                Move(PieceType.P.asBlack, Square.G7, Square.G5),
                Move(PieceType.P.asBlack, Square.H7, Square.H5),
                Move(PieceType.N.asBlack, Square.B8, Square.A6),
                Move(PieceType.N.asBlack, Square.B8, Square.C6),
                Move(PieceType.N.asBlack, Square.G8, Square.F6),
                Move(PieceType.N.asBlack, Square.G8, Square.H6)
            )
        }

        @JvmStatic
        private fun movesAfterE4E5(): List<Move> {
            return listOf(
                Move(PieceType.P.asWhite, Square.A2, Square.A3),
                Move(PieceType.P.asWhite, Square.B2, Square.B3),
                Move(PieceType.P.asWhite, Square.C2, Square.C3),
                Move(PieceType.P.asWhite, Square.D2, Square.D3),
                Move(PieceType.P.asWhite, Square.F2, Square.F3),
                Move(PieceType.P.asWhite, Square.G2, Square.G3),
                Move(PieceType.P.asWhite, Square.H2, Square.H3),
                Move(PieceType.P.asWhite, Square.A2, Square.A4),
                Move(PieceType.P.asWhite, Square.B2, Square.B4),
                Move(PieceType.P.asWhite, Square.C2, Square.C4),
                Move(PieceType.P.asWhite, Square.D2, Square.D4),
                Move(PieceType.P.asWhite, Square.F2, Square.F4),
                Move(PieceType.P.asWhite, Square.G2, Square.G4),
                Move(PieceType.P.asWhite, Square.H2, Square.H4),
                Move(PieceType.N.asWhite, Square.B1, Square.A3),
                Move(PieceType.N.asWhite, Square.B1, Square.C3),
                Move(PieceType.N.asWhite, Square.G1, Square.F3),
                Move(PieceType.N.asWhite, Square.G1, Square.H3),
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
