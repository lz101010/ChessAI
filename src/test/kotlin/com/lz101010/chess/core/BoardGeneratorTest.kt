// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.*
import com.lz101010.chess.data.PieceType.P
import com.lz101010.chess.data.PieceType.R
import com.lz101010.chess.data.PieceType.N
import com.lz101010.chess.data.PieceType.B
import com.lz101010.chess.data.PieceType.Q
import com.lz101010.chess.data.PieceType.K
import com.lz101010.chess.data.CastlingOption.WHITE_K
import com.lz101010.chess.data.CastlingOption.WHITE_Q
import com.lz101010.chess.data.CastlingOption.BLACK_K
import com.lz101010.chess.data.CastlingOption.BLACK_Q
import com.lz101010.chess.support.OpeningMoves
import com.lz101010.chess.support.move
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardGeneratorTest {
    @Test
    fun defaultBoardFen_passes() {
        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"))
            .isEqualTo(Board.default)
    }

    @Test
    fun fenAfterSomeMoves_passes() {
        val board = Board.default
            .move(OpeningMoves.E4)
            .move(OpeningMoves.Nf6)
            .move(OpeningMoves.D4)
            .move(Move(N.asBlack, Square.F6, Square.E4))
            .move(OpeningMoves.A3)

        assertThat(BoardGenerator.fromFen("rnbqkb1r/pppppppp/8/8/3Pn3/P7/1PP2PPP/RNBQKBNR b KQkq - 0 3"))
            .isEqualTo(board)
    }

    @Test
    fun endgameBoardFen_passes() {
        val pieces = arrayOf(
            makeEmptyRow(),
            arrayOf(null, null, P.asWhite, null, null, null, null, null),
            arrayOf(null, null, null, null, K.asWhite, null, null, null),
            makeEmptyRow(),
            makeEmptyRow(),
            arrayOf(null, K.asBlack, null, null, null, null, null, null),
            makeEmptyRow(),
            makeEmptyRow()
        )

        val board = Board(pieces = pieces, castlingOptions = listOf())

        assertThat(BoardGenerator.fromFen("8/2P5/4K3/8/8/1k6/8/8 w - - 0 1")).isEqualTo(board)
    }

    @Test
    fun fenWithEverythingInGroup1_passes() {
        val pieces = arrayOf(
            makeEmptyRow(),
            arrayOf(P.asWhite, P.asBlack, B.asBlack, Q.asWhite, R.asBlack, N.asBlack, Q.asBlack, null),
            arrayOf(null, B.asWhite, null, null, null, null, null, null),
            arrayOf(null, null, N.asWhite, null, null, null, null, null),
            arrayOf(null, null, null, R.asWhite, null, null, null, null),
            arrayOf(null, null, null, null, K.asBlack, null, null, null),
            arrayOf(null, null, null, null, null, P.asWhite, null, null),
            arrayOf(K.asWhite, null, null, null, null, null, null, null)
        )

        val board = Board(pieces = pieces, castlingOptions = listOf())

        assertThat(BoardGenerator.fromFen("8/PpbQrnq1/1B6/2N5/3R4/4k3/5P2/K7 w - - 0 1")).isEqualTo(board)
    }

    @Test
    fun defaultBoardFen_withFewerCastlingOptions_passes() {
        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQk - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(WHITE_K, WHITE_Q, BLACK_K)))
        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQq - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(WHITE_K, WHITE_Q, BLACK_Q)))
        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Kkq - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(WHITE_K, BLACK_K, BLACK_Q)))
        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Qkq - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(WHITE_Q, BLACK_K, BLACK_Q)))

        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQ - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(WHITE_K, WHITE_Q)))
        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Kk - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(WHITE_K, BLACK_K)))
        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Kq - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(WHITE_K, BLACK_Q)))
        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Qk - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(WHITE_Q, BLACK_K)))
        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Qq - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(WHITE_Q, BLACK_Q)))
        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w kq - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(BLACK_K, BLACK_Q)))

        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w K - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(WHITE_K)))
        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w Q - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(WHITE_Q)))
        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w k - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(BLACK_K)))
        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w q - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf(BLACK_Q)))

        assertThat(BoardGenerator.fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1"))
            .isEqualTo(Board.default.copy(castlingOptions = setOf()))
    }

    private fun makeEmptyRow(): Array<Piece?> = arrayOfNulls(8)
}
