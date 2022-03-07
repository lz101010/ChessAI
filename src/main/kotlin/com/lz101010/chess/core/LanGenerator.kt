// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Game
import com.lz101010.chess.data.Move

object LanGenerator {
    fun generate(game: Game): String {
        return game.moves
            .foldIndexed(State()) { index, state, move -> state.move(move, index) }
            .moves.joinToString("\n")
    }

    private class State(var board: Board = Board.default, var moves: MutableList<String> = mutableListOf()) {
        fun move(move: Move, index: Int): State {
            board = MoveMaker.move(board, move)
            val whiteToMove = index % 2 == 0
            // TODO: add result (1-0, 0-1, 0.5-0.5)
            // TODO: add castling (O-O, O-O-O)
            // TODO: remove Move.piece
            val suffix = makeSuffix(board)

            if (whiteToMove) {
                moves.add("${1 + index / 2}. ${move.pretty}$suffix *")
            } else {
                moves.add("${moves.removeLast().dropLast(1)}${move.pretty}$suffix")
            }
            return this
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
