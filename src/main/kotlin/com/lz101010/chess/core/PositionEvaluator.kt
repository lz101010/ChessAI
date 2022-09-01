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
            return if (isOver(board)) Event.MATE else Event.CHECK
        }
        if (isOver(board) || board.plies > 100u) {
            return Event.STALE_MATE
        }
        return Event.NONE
    }

    fun isMate(board: Board): Boolean = evaluate(board) == Event.MATE

    fun isStaleMate(board: Board): Boolean = evaluate(board) == Event.STALE_MATE

    fun isCheck(board: Board): Boolean {
        val target = Square.values().first { board[it] == Piece(PieceType.K, board.whiteToMove) }

        val attackedSquares = AttackedSquaresGenerator.generate(board)

        return attackedSquares.contains(target)
    }

    fun isOver(board: Board) = MoveGenerator.find(board).isEmpty()
}
