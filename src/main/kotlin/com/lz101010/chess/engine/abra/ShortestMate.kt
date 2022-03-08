// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.abra

import com.lz101010.chess.core.MoveMaker
import com.lz101010.chess.core.PositionEvaluator
import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import java.lang.Integer.min

private const val MAX_MATE_DEPTH = 12

internal object ShortestMate {
    fun find(board: Board, bestMoves: List<Move>, abraSearchDepth: Int): List<Move> {
        var minDepth = MAX_MATE_DEPTH
        val result = mutableMapOf<Move, Int>()
        for (move in bestMoves) {
            val depth = findDepth(board, move, abraSearchDepth, 0, minDepth)
            minDepth = min(minDepth, depth)
            result[move] = depth
        }

        return result.filter { it.value == minDepth }.map { it.key }
    }

    private fun findDepth(
        board: Board,
        move: Move,
        abraSearchDepth: Int,
        currentDepth: Int,
        currentMinimalDepth: Int
    ): Int {
        if (currentDepth > currentMinimalDepth || abraSearchDepth < 1) {
            return currentDepth
        }
        val nextBoard = MoveMaker.move(board, move)
        return if (PositionEvaluator.isMate(nextBoard)) {
            currentDepth
        } else {
            val nextMove = AbraEngine(abraSearchDepth).nextMove(nextBoard)
            findDepth(nextBoard, nextMove, abraSearchDepth, currentDepth + 1, currentMinimalDepth)
        }
    }
}
