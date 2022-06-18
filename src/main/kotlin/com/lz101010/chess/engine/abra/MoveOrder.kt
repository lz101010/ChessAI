// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.abra

import com.lz101010.chess.core.MoveGenerator
import com.lz101010.chess.core.MoveMaker
import com.lz101010.chess.core.PositionEvaluator
import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move

internal object MoveOrder {
    fun orderPositions(board: Board): List<Board> {
        return MoveGenerator.find(board).map { MoveMaker.move(board, it).copy(lastMoves = join(it, board)) }.let(::order)
    }

    fun order(positions: Collection<Board>): List<Board> {
        return positions.sortedBy { position ->
            when (PositionEvaluator.evaluate(position)) {
                PositionEvaluator.Event.MATE -> 0
                PositionEvaluator.Event.CHECK -> 1
                PositionEvaluator.Event.NONE -> 2
                PositionEvaluator.Event.STALE_MATE -> 3
            }
        }
    }
}

private fun join(move: Move, board: Board): List<String> {
    val result = mutableListOf<String>()
    result.addAll(board.lastMoves)
    result.add("${board[move.from]!!.basic}$move")
    return result
}
