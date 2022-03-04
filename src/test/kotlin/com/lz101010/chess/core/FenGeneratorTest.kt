// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import com.lz101010.chess.data.PieceType
import com.lz101010.chess.data.Square
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class FenGeneratorTest {
    @Test
    fun emptyBoardFen_passes() {
        assertDoesNotThrow { FenGenerator.generate(Board.empty) }
    }

    @Test
    fun defaultBoardFen_passes() {
        assertThat(FenGenerator.generate(Board()))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
    }

    @Test
    fun e4Fen_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, Move(PieceType.P.asWhite, from = Square.E2, to = Square.E4))

        assertThat(FenGenerator.generate(boardAfterE4))
            .isEqualTo("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 1 1")
    }

    @Test
    fun e4e5Fen_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, Move(PieceType.P.asWhite, from = Square.E2, to = Square.E4))
        val boardAfterE5 = MoveMaker.move(boardAfterE4, Move(PieceType.P.asBlack, from = Square.E7, to = Square.E5))

        assertThat(FenGenerator.generate(boardAfterE5))
            .isEqualTo("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 2 2")
    }
}
