// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import org.assertj.core.api.Assertions.assertThat
import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import com.lz101010.chess.data.PieceType
import com.lz101010.chess.data.Square
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MoveMakerTest {
    @Test
    fun movePe2e4_passes() {
        val defaultBoard = Board()
        val boardAfterE4 = MoveMaker.move(defaultBoard, Move(PieceType.P.asWhite, from = Square.E2, to = Square.E4))

        assertThat(boardAfterE4[Square.E4]).isEqualTo(PieceType.P.asWhite)
        assertThat(boardAfterE4[Square.E2]).isNull()

        for (square in Square.values().filterNot { it == Square.E2 || it == Square.E4 }) {
            val defaultPiece = defaultBoard[square]
            if (defaultPiece == null) {
                assertThat(boardAfterE4[square]).isNull()
            } else {
                assertThat(boardAfterE4[square]).isEqualTo(defaultPiece)
            }
        }
    }

    @Test
    fun moveBf1e2_fails() {
        val defaultBoard = Board()

        assertThrows<IllegalArgumentException> {
            MoveMaker.move(defaultBoard, Move(PieceType.B.asWhite, from = Square.F1, to = Square.E2))
        }.let {
            assertThat(it.message).isEqualTo("${Square.E2} is occupied by same color piece")
        }
    }

    @Test
    fun moveNf1e3_fails() {
        val defaultBoard = Board()

        assertThrows<IllegalArgumentException> {
            MoveMaker.move(defaultBoard, Move(PieceType.N.asWhite, from = Square.F1, to = Square.E3))
        }.let {
            assertThat(it.message).isEqualTo("${PieceType.B.asWhite} != ${PieceType.N.asWhite}")
        }
    }

    @Test
    fun moveNg8f6_fails() {
        val defaultBoard = Board()

        assertThrows<IllegalArgumentException> {
            MoveMaker.move(defaultBoard, Move(PieceType.N.asBlack, from = Square.G8, to = Square.F6))
        }.let {
            assertThat(it.message).isEqualTo("white to move")
        }
    }
}
