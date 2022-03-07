// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Game
import com.lz101010.chess.data.Move

object LanGenerator {
    fun generate(game: Game, board: Board = Board.default): String {
        return game.moves
            .foldIndexed(State(board)) { index, state, move -> state.move(move, index) }
            .moves.joinToString("\n")
    }

    private class State(var board: Board, val moves: MutableList<String> = mutableListOf()) {
        fun move(move: Move, index: Int): State {
            val movePretty = castling(board, move) ?: move.pretty
            val whiteToMove = index % 2 == 0

            board = MoveMaker.move(board, move)
            val suffix = makeSuffix(board)

            // TODO: add result (1-0, 0-1, 0.5-0.5)
            // TODO: remove Move.piece

            if (whiteToMove) {
                moves.add("${1 + index / 2}. ${movePretty}$suffix *")
            } else {
                moves.add("${moves.removeLast().dropLast(1)}${movePretty}$suffix")
            }
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

        private fun makeSuffix(board: Board): String {
            if (PositionEvaluator.isMate(board)) {
                return "#"
            }
            if (PositionEvaluator.isCheck(board)) {
                return "+"
            }
            return ""
        }
    }
}
