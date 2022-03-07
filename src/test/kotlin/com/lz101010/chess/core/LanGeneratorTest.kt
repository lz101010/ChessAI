// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.Game
import com.lz101010.chess.data.Move
import com.lz101010.chess.data.PieceType
import com.lz101010.chess.data.Square
import com.lz101010.chess.support.OpeningMoves
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LanGeneratorTest {
    @Test
    fun defaultBoardLan_passes() {
        assertThat(LanGenerator.generate(after())).isBlank
    }

    @Test
    fun boardOpeningLan_passes() {
        assertThat(LanGenerator.generate(after(OpeningMoves.E4)))
            .isEqualTo("1. ♙e2e4 *")
        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.E5)))
            .isEqualTo("1. ♙e2e4 ♟e7e5")

        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.E5, OpeningMoves.Nf3)))
            .isEqualTo("""
                1. ♙e2e4 ♟e7e5
                2. ♘g1f3 *
            """.trimIndent())
        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.E5, OpeningMoves.Nf3, OpeningMoves.Nc6)))
            .isEqualTo("""
                1. ♙e2e4 ♟e7e5
                2. ♘g1f3 ♞b8c6
            """.trimIndent())
    }

    @Test
    fun checkLan_passes() {
        assertThat(LanGenerator.generate(after(OpeningMoves.E4)))
            .isEqualTo("1. ♙e2e4 *")
        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.E5)))
            .isEqualTo("1. ♙e2e4 ♟e7e5")

        val qH5 = Move(PieceType.Q.asWhite, Square.D1, Square.H5)
        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.E5, qH5)))
            .isEqualTo("""
                1. ♙e2e4 ♟e7e5
                2. ♕d1h5 *
            """.trimIndent())
        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.E5, qH5, OpeningMoves.G6)))
            .isEqualTo("""
                1. ♙e2e4 ♟e7e5
                2. ♕d1h5 ♟g7g6
            """.trimIndent())

        val qE5 = Move(PieceType.Q.asWhite, Square.H5, Square.E5)
        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.E5, qH5, OpeningMoves.G6, qE5)))
            .isEqualTo("""
                1. ♙e2e4 ♟e7e5
                2. ♕d1h5 ♟g7g6
                3. ♕h5e5+ *
            """.trimIndent())
    }

    @Test
    fun foolsMateWhiteLan_passes() {
        assertThat(LanGenerator.generate(after(OpeningMoves.F3)))
            .isEqualTo("1. ♙f2f3 *")
        assertThat(LanGenerator.generate(after(OpeningMoves.F3, OpeningMoves.E5)))
            .isEqualTo("1. ♙f2f3 ♟e7e5")
        assertThat(LanGenerator.generate(after(OpeningMoves.F3, OpeningMoves.E5, OpeningMoves.G4)))
            .isEqualTo("""
                1. ♙f2f3 ♟e7e5
                2. ♙g2g4 *
            """.trimIndent())

        val qH4 = Move(PieceType.Q.asBlack, Square.D8, Square.H4)
        assertThat(LanGenerator.generate(after(OpeningMoves.F3, OpeningMoves.E5, OpeningMoves.G4, qH4)))
            .isEqualTo("""
                1. ♙f2f3 ♟e7e5
                2. ♙g2g4 ♛d8h4#
                0-1
            """.trimIndent())
    }

    @Test
    fun foolsMateBlackLan_passes() {
        assertThat(LanGenerator.generate(after(OpeningMoves.E4)))
            .isEqualTo("1. ♙e2e4 *")
        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.F6)))
            .isEqualTo("1. ♙e2e4 ♟f7f6")
        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.F6, OpeningMoves.D4)))
            .isEqualTo("""
                1. ♙e2e4 ♟f7f6
                2. ♙d2d4 *
            """.trimIndent())
        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.F6, OpeningMoves.D4, OpeningMoves.G5)))
            .isEqualTo("""
                1. ♙e2e4 ♟f7f6
                2. ♙d2d4 ♟g7g5
            """.trimIndent())

        val qH5 = Move(PieceType.Q.asWhite, Square.D1, Square.H5)
        assertThat(LanGenerator.generate(after(OpeningMoves.E4, OpeningMoves.F6, OpeningMoves.D4, OpeningMoves.G5, qH5)))
            .isEqualTo("""
                1. ♙e2e4 ♟f7f6
                2. ♙d2d4 ♟g7g5
                3. ♕d1h5#
                1-0
            """.trimIndent())
    }

    @Test
    fun castling1_passes() {
        val board = BoardGenerator.fromFen("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1")
        val oo = Move(PieceType.K.asWhite, Square.E1, Square.G1)
        val ooo = Move(PieceType.K.asBlack, Square.E8, Square.C8)

        assertThat(LanGenerator.generate(after(oo), board))
            .isEqualTo("1. O-O *")

        assertThat(LanGenerator.generate(after(oo, ooo), board))
            .isEqualTo("1. O-O o-o-o")
    }

    @Test
    fun castling2_passes() {
        val board = BoardGenerator.fromFen("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1")
        val ooo = Move(PieceType.K.asWhite, Square.E1, Square.C1)
        val oo = Move(PieceType.K.asBlack, Square.E8, Square.G8)

        assertThat(LanGenerator.generate(after(ooo), board))
            .isEqualTo("1. O-O-O *")

        assertThat(LanGenerator.generate(after(ooo, oo), board))
            .isEqualTo("1. O-O-O o-o")
    }

    @Test
    fun staleMatePasses() {
        val board = BoardGenerator.fromFen("k1K5/8/5p1Q/5P2/8/8/8/8 w - - 0 1")
        val move = Move(PieceType.Q.asWhite, Square.H6, Square.H7)

        assertThat(LanGenerator.generate(after(move), board))
            .isEqualTo("""
                1. ♕h6h7
                0.5-0.5
            """.trimIndent())
    }

    private fun after(vararg moves: Move): Game {
        return Game(moves.toList())
    }
}
