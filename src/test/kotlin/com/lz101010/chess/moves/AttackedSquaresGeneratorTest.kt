// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.moves

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Square
import com.lz101010.chess.moves.AttackedSquaresGenerator
import com.lz101010.chess.support.OpeningMoves
import com.lz101010.chess.support.move
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AttackedSquaresGeneratorTest {
    @Test
    fun defaultBoardAttackedSquares_passes() {
        val board = Board.default

        assertThat(AttackedSquaresGenerator.generate(board))
            .containsOnly(Square.A6, Square.B6, Square.C6, Square.D6, Square.E6, Square.F6, Square.G6, Square.H6)
    }

    @Test
    fun boardAfterE4AttackedSquares_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)

        assertThat(AttackedSquaresGenerator.generate(board))
            .containsOnly(
                Square.A3, Square.B3, Square.C3, Square.D3, Square.E3, Square.F3, Square.G3, Square.H3,
                Square.F5, Square.D5,
                Square.E2, Square.G4, Square.H5,
                Square.C4, Square.B5, Square.A6
            )
    }

    @Test
    fun boardAfterE4E5AttackedSquares_passes() {
        val board = Board.default
            .move(OpeningMoves.E4).move(OpeningMoves.E5)

        assertThat(AttackedSquaresGenerator.generate(board))
            .containsOnly(
                Square.A6, Square.B6, Square.C6, Square.D6, Square.E6, Square.F6, Square.G6, Square.H6,
                Square.F4, Square.D4,
                Square.E7, Square.G5, Square.H4,
                Square.C5, Square.B4, Square.A3
            )
    }

    @Test
    fun boardAfterE4E5Nf3AttackedSquares_passes() {
        val board = Board.default
            .move(OpeningMoves.E4).move(OpeningMoves.E5).move(OpeningMoves.Nf3)

        assertThat(AttackedSquaresGenerator.generate(board))
            .containsOnly(
                Square.A3, Square.B3, Square.C3, Square.D3, Square.E3, Square.G3, Square.H3,
                Square.D4, Square.E5, Square.G1, Square.G5, Square.H4,
                Square.F5, Square.D5,
                Square.E2, Square.C4, Square.B5, Square.A6
            )
    }

    @Test
    fun boardAfterE4E5Nf3Nc6AttackedSquares_passes() {
        val board = Board.default
            .move(OpeningMoves.E4).move(OpeningMoves.E5).move(OpeningMoves.Nf3).move(OpeningMoves.Nc6)

        assertThat(AttackedSquaresGenerator.generate(board))
            .containsOnly(
                Square.A6, Square.B6, Square.D6, Square.E6, Square.F6, Square.G6, Square.H6,
                Square.A5, Square.B8, Square.B4, Square.D4,
                Square.F4, Square.D4,
                Square.E7, Square.G5, Square.H4,
                Square.C5, Square.B4, Square.A3
            )
    }
}
