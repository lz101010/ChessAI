// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.engine.abra

import com.lz101010.chess.core.MoveMaker
import com.lz101010.chess.core.PositionEvaluator
import com.lz101010.chess.data.Board
import com.lz101010.chess.data.Move
import java.lang.Integer.max

private const val MAX_MATE_DEPTH = 12

internal object LongestMate {
    fun find(board: Board, bestMoves: List<Move>, abraSearchDepth: Int): List<Move> {
        var maxDepth = 0
        val result = mutableMapOf<Move, Int>()
        for (move in bestMoves) {
            val depth = findDepth(board, move, abraSearchDepth, 0)
            maxDepth = max(maxDepth, depth)
            result[move] = depth
        }

        return result.filter { it.value == maxDepth }.map { it.key }
    }

    private fun findDepth(
        board: Board,
        move: Move,
        abraSearchDepth: Int,
        currentDepth: Int
    ): Int {
        if (currentDepth > MAX_MATE_DEPTH || abraSearchDepth < 1) {
            return currentDepth
        }
        val nextBoard = MoveMaker.move(board, move)
        return if (PositionEvaluator.isMate(nextBoard)) {
            currentDepth
        } else {
            val nextMove = AbraEngine(abraSearchDepth).nextMove(nextBoard)
            findDepth(nextBoard, nextMove, abraSearchDepth, currentDepth + 1)
        }
    }
}
