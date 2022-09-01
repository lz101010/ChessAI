// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.notation

import com.lz101010.chess.core.MoveEvaluator
import com.lz101010.chess.core.MoveMaker
import com.lz101010.chess.core.PositionEvaluator
import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move

class GameState(
    private var board: Board,
    val moves: MutableList<String> = mutableListOf(),
    private val prettyPiece: Boolean = true,
    private val printMove: (Move) -> String
) {
    fun move(move: Move, index: Int): GameState {
        val piece = board[move.from] ?: throw IllegalArgumentException("${move.from} is empty")
        val movePretty = castling(board, move) ?: "${if (prettyPiece) piece.pretty else piece.basic}${printMove(move)}"
        val whiteToMove = index % 2 == 0

        board = MoveMaker.move(board, move)
        val suffix = checkSuffix(board)
        val gameResult = gameResult(board)

        if (whiteToMove) {
            moves.add("${1 + index / 2}. ${movePretty}$suffix${if (gameResult == null) " *" else ""}")
        } else {
            moves.add("${moves.removeLast().dropLast(1)}${movePretty}$suffix")
        }
        gameResult(board)?.let(moves::add)
        return this
    }

    private fun castling(board: Board, move: Move): String? {
        if (MoveEvaluator.isKingSideCastleWhite(board, move)) {
            return "O-O"
        } else if (MoveEvaluator.isQueenSideCastleWhite(board, move)) {
            return "O-O-O"
        } else if (MoveEvaluator.isKingSideCastleBlack(board, move)) {
            return "o-o"
        } else if (MoveEvaluator.isQueenSideCastleBlack(board, move)) {
            return "o-o-o"
        }
        return null
    }

    private fun checkSuffix(board: Board): String {
        if (PositionEvaluator.isMate(board)) {
            return "#"
        }
        if (PositionEvaluator.isCheck(board)) {
            return "+"
        }
        return ""
    }

    private fun gameResult(board: Board): String? {
        if (PositionEvaluator.isMate(board)) {
            return if (board.whiteToMove) "0-1" else "1-0"
        }
        if (PositionEvaluator.isStaleMate(board)) {
            return "0.5-0.5"
        }
        return null
    }
}
