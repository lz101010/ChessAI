// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.game

object LanGenerator {
    fun generate(game: Game, board: Board = Board.default): String {
        return game.moves
            .foldIndexed(State(board)) { index, state, move -> state.move(move, index) }
            .moves.joinToString("\n")
    }

    private class State(var board: Board, val moves: MutableList<String> = mutableListOf()) {
        fun move(move: Move, index: Int): State {
            val piece = board[move.from] ?: throw IllegalArgumentException("${move.from} is empty")
            val movePretty = castling(board, move) ?: "${piece.pretty}$move"
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
}
