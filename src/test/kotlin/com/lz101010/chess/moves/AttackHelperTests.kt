package com.lz101010.chess.moves

import com.lz101010.chess.core.BoardGenerator
import com.lz101010.chess.data.Square
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class AttackHelperTests {
    @Test
    fun attackedSquares_passes() {
        val board = BoardGenerator.fromFen("1K6/1B3P1P/8/8/4N3/8/6PR/1k6 w - - 0 1")

        val attackedSquares = listOf(
            Square.A8, Square.C8, Square.A7, Square.C7, // attacked by king
            Square.E8, Square.G8, Square.G3, Square.F3, Square.H3, // attacked by pawns
            Square.A6, Square.C6, Square.D5, // attacked by bishop
            Square.D6, Square.C5, Square.C3, Square.D2, Square.F2, Square.G3, Square.G5, Square.F6, // attacked by knight
            Square.H1, Square.H3, Square.H4, Square.H5, Square.H6 // attacked by rook
        )

        for (square in Square.values()) {
            if (square in attackedSquares) {
                assertThat(AttackHelper.isAttacked(square, byWhite = true, board)).isTrue
            } else {
                assertThat(AttackHelper.isAttacked(square, byWhite = true, board)).withFailMessage { "$square" }.isFalse
            }
        }
    }

    @Test
    fun countAttackedSquares_passes() {
        val board = BoardGenerator.fromFen("1K6/1B3P1P/8/8/4N3/8/6PR/1k6 w - - 0 1")

        val doubleAttackedSquares = listOf(
            Square.A8, Square.C8, Square.G8, Square.H3
        )

        for (square in doubleAttackedSquares) {
            assertThat(AttackHelper.countAttacks(Square.A8, byWhite = true, board)).isEqualTo(2)
        }
    }
}
