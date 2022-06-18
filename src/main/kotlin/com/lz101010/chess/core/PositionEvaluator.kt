// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core

import com.lz101010.chess.data.*

object PositionEvaluator {
    enum class Event {
        NONE,
        CHECK,
        MATE,
        STALE_MATE
    }

    fun evaluate(board: Board): Event {
        if (isCheck(board)) {
            return if (noMovesLeft(board)) Event.MATE else Event.CHECK
        }
        if (noMovesLeft(board) || board.plies > 100u) {
            return Event.STALE_MATE
        }
        return Event.NONE
    }

    fun isOver(board: Board): Boolean {
        val event = evaluate(board)
        return event == Event.MATE || event == Event.STALE_MATE
    }

    fun isMate(board: Board): Boolean = evaluate(board) == Event.MATE

    fun isStaleMate(board: Board): Boolean = evaluate(board) == Event.STALE_MATE

    fun isCheck(board: Board): Boolean {
        val target = Square.values().first { board[it] == Piece(PieceType.K, board.whiteToMove) }

        val attackedSquares = AttackedSquaresGenerator.generate(board)

        return attackedSquares.contains(target)
    }

    private fun noMovesLeft(board: Board) = MoveGenerator.find(board).isEmpty()
}
