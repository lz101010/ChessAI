// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.random

import com.lz101010.chess.data.Move
import com.lz101010.chess.data.PieceType
import com.lz101010.chess.data.Square
import com.lz101010.chess.engine.abra.AbraEngine
import com.lz101010.chess.game.Game
import com.lz101010.chess.support.OpeningMoves
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

private const val MAX_MOVES = 1_000

class RandomEngineTest {

    @OptIn(ExperimentalTime::class)
    @RepeatedTest(1)
    fun initialBoardPrints_passes() {
        val engineWhite = AbraEngine()
        val engineBlack = RandomEngine

        val game = Game()
        game.move(OpeningMoves.E4)
        val referenceBoard = com.github.bhlangonijr.chesslib.Board()
        referenceBoard.doMove(convert(OpeningMoves.E4, true))

        while (game.moves.size < MAX_MOVES && !game.isOver()) {
            val engine = if (game.whiteToMove()) engineWhite else engineBlack

            println(game.fen())
            val timedValue = measureTimedValue {
                game.findMove(engine)
            }
            val nextMove = timedValue.value
            println("took ${timedValue.duration} to find $nextMove")
            game.move(nextMove)
            println(game)

            referenceBoard.doMove(convert(nextMove, !game.whiteToMove()))
            assertThat(game.fen())
                .withFailMessage { "${game.fen()}\n${referenceBoard.fen}\n${game.lan()}" }
                .isEqualTo(referenceBoard.fen)
        }

        assertThat(game.moves.size).isLessThan(MAX_MOVES)
        println(game.lan())
    }

    @Test
    fun compareFen_passes() {
        compareFen(
            OpeningMoves.F4, OpeningMoves.D6,
            OpeningMoves.D4, Move(Square.C8, Square.H3),
            OpeningMoves.C3, Move(Square.B8, Square.D7),
            Move(Square.F4, Square.F5), Move(Square.H3, Square.G2),
            Move(Square.C3, Square.C4), Move(Square.G2, Square.H1)
        )
        compareFen(
            OpeningMoves.C3, OpeningMoves.A6,
            OpeningMoves.F4, OpeningMoves.Nc6,
            Move(Square.D1, Square.C2), OpeningMoves.E5,
            Move(Square.C2, Square.G6), OpeningMoves.D6,
            Move(Square.G6, Square.E6), Move(Square.G8, Square.E7),
            Move(Square.E6, Square.F6), Move(Square.E7, Square.D5),
            OpeningMoves.H3, Move(Square.C8, Square.D7),
            Move(Square.F6, Square.E5), Move(Square.D8, Square.E7),
            Move(Square.E5, Square.G7), Move(Square.E7, Square.G5),
            Move(Square.H1, Square.H2), Move(Square.G5, Square.F4),
            Move(Square.H2, Square.H1), Move(Square.F4, Square.C4),
            OpeningMoves.D4, Move(Square.A6, Square.A5),
            OpeningMoves.A4, Move(Square.C6, Square.E5),
            Move(Square.G7, Square.H8)
        )
        compareFen(
            OpeningMoves.C4, OpeningMoves.C6,
            OpeningMoves.A4, Move(Square.C6, Square.C5),
            Move(Square.A1, Square.A3), OpeningMoves.B5,
            Move(Square.A3, Square.H3), OpeningMoves.Nf6,
            Move(Square.H3, Square.C3)
        )
        compareFen(
            OpeningMoves.A4, OpeningMoves.A5,
            Move(Square.A1, Square.A3), OpeningMoves.E5,
            Move(Square.A3, Square.B3), OpeningMoves.D5,
            Move(Square.B3, Square.B6), OpeningMoves.F5,
            Move(Square.B6, Square.A6), OpeningMoves.C5,
            Move(Square.A6, Square.A8)
        )
        compareFen(
            OpeningMoves.A4, OpeningMoves.A5,
            OpeningMoves.B4, OpeningMoves.B5,
            Move(Square.A4, Square.B5), Move(Square.A5, Square.B4),
            Move(Square.A1, Square.A8)
        )
    }

    private fun compareFen(vararg moves: Move) {
        val game = Game()
        val referenceBoard = com.github.bhlangonijr.chesslib.Board()

        moves.forEach { move ->
            game.move(move)
            referenceBoard.doMove(convert(move, !game.whiteToMove()))
        }

        assertThat(game.fen()).isEqualTo(referenceBoard.fen)
    }

    private fun convert(nextMove: Move, white: Boolean): com.github.bhlangonijr.chesslib.move.Move {
        val from = convert(nextMove.from)
        val to = convert(nextMove.to)
        val promotion = nextMove.promotion?.let { convert(it, white) }
            ?: return com.github.bhlangonijr.chesslib.move.Move(from, to)
        return com.github.bhlangonijr.chesslib.move.Move(from, to, promotion)
    }

    private fun convert(square: Square): com.github.bhlangonijr.chesslib.Square {
        return com.github.bhlangonijr.chesslib.Square.values().first { it.name == square.name }
    }

    private fun convert(pieceType: PieceType, white: Boolean): com.github.bhlangonijr.chesslib.Piece {
        return when (pieceType) {
            PieceType.P -> if (white) com.github.bhlangonijr.chesslib.Piece.WHITE_PAWN else com.github.bhlangonijr.chesslib.Piece.BLACK_PAWN
            PieceType.R -> if (white) com.github.bhlangonijr.chesslib.Piece.WHITE_ROOK else com.github.bhlangonijr.chesslib.Piece.BLACK_ROOK
            PieceType.N -> if (white) com.github.bhlangonijr.chesslib.Piece.WHITE_KNIGHT else com.github.bhlangonijr.chesslib.Piece.BLACK_KNIGHT
            PieceType.B -> if (white) com.github.bhlangonijr.chesslib.Piece.WHITE_BISHOP else com.github.bhlangonijr.chesslib.Piece.BLACK_BISHOP
            PieceType.Q -> if (white) com.github.bhlangonijr.chesslib.Piece.WHITE_QUEEN else com.github.bhlangonijr.chesslib.Piece.BLACK_QUEEN
            PieceType.K -> if (white) com.github.bhlangonijr.chesslib.Piece.WHITE_KING else com.github.bhlangonijr.chesslib.Piece.BLACK_KING
        }
    }
}
